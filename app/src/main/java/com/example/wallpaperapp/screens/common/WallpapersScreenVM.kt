package com.example.wallpaperapp.screens.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wallpaperapp.data.Wallpaper
import com.example.wallpaperapp.data.WallpaperRepository
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class WallpapersScreenVM(sourceName: String, private val wallpapersRepo: WallpaperRepository) : ViewModel() {

    val userId = Firebase.auth.currentUser?.uid ?: ""

    val homeUiState: StateFlow<HomeUiState> =
        wallpapersRepo.getWallpapers(sourceName, userId)
            .map { HomeUiState(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000L),
                initialValue = HomeUiState()
            )

    fun changeFav(wallpaper: Wallpaper) {
        viewModelScope.launch {
            wallpapersRepo.changeFavourite(wallpaper, userId)
        }
    }

    data class HomeUiState(val itemList: List<Wallpaper> = listOf(), val isLoading: Boolean = false)

}