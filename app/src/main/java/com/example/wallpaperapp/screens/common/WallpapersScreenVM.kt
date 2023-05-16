package com.example.wallpaperapp.screens.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wallpaperapp.data.Wallpaper
import com.example.wallpaperapp.data.WallpaperRepository
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class WallpapersScreenVM : ViewModel() {

    private var pWallpapers = MutableStateFlow(listOf<Wallpaper>())
    val wallpapers: StateFlow<List<Wallpaper>> = pWallpapers.asStateFlow()

    private val wallpapersRepo = WallpaperRepository()

    init {
        updateWallpapers()
    }

    private fun updateWallpapers() {
        wallpapersRepo.update(pWallpapers)
    }

    fun changeFav(wallpaper: Wallpaper) {
        viewModelScope.launch {
            wallpapersRepo.changeFavourite(
                wallpaper,
                userId = Firebase.auth.currentUser?.uid ?: ""
            )
        }
    }

}