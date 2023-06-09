package com.example.wallpaperapp.screens.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wallpaperapp.data.Wallpaper
import com.example.wallpaperapp.data.WallpaperRepository
import com.example.wallpaperapp.user.User
import com.example.wallpaperapp.user.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class WallpapersScreenVM(
    sourceName: String,
    private val wallpapersRepo: WallpaperRepository,
    private val userRepo: UserRepository
) : ViewModel() {

    lateinit var authState: StateFlow<User> //=
    lateinit var homeUiState: StateFlow<HomeUiState>// =

    init {

        viewModelScope.launch {
            val initAuthState = userRepo.getAppUser()
            authState = userRepo.getAuthState()
                .flowOn(Dispatchers.IO)
                .stateIn(
                    scope = viewModelScope,
                    started = SharingStarted.WhileSubscribed(5000L),
                    initialValue = initAuthState
                )
            authState.collect { user ->
                homeUiState = wallpapersRepo.getWallpapers(sourceName, user.id)
                    .map { HomeUiState(itemList = it) }
                    .stateIn(
                        scope = viewModelScope,
                        started = SharingStarted.WhileSubscribed(5000L),
                        initialValue = HomeUiState()
                    )
            }
        }
    }

    fun changeFav(wallpaper: Wallpaper) {
        viewModelScope.launch {
            wallpapersRepo.changeFavourite(wallpaper, authState.value.id)
        }
    }

    fun signOut() {
        viewModelScope.launch {
            userRepo.signOut()
        }
    }

    data class HomeUiState(val itemList: List<Wallpaper> = listOf())

}