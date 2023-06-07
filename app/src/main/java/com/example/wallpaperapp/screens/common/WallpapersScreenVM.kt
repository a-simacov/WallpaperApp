package com.example.wallpaperapp.screens.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wallpaperapp.data.Wallpaper
import com.example.wallpaperapp.data.WallpaperRepository
import com.example.wallpaperapp.user.User
import com.example.wallpaperapp.user.UserRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class WallpapersScreenVM(
    sourceName: String,
    private val wallpapersRepo: WallpaperRepository,
    private val userRepo: UserRepository
) : ViewModel() {

    lateinit var authState: StateFlow<User>// =
//        userRepo.getAuthState()
//            .stateIn(
//                scope = viewModelScope,
//                started = SharingStarted.WhileSubscribed(5000L),
//                initialValue = userRepo.getAppUser()//User(isAuth = false, id = "empty")
//            )

    lateinit var homeUiState: StateFlow<HomeUiState>// =
//        wallpapersRepo.getWallpapers(sourceName, userRepo.getAppUser().id)
//            .map { HomeUiState(itemList = it) }
//            .stateIn(
//                scope = viewModelScope,
//                started = SharingStarted.WhileSubscribed(5000L),
//                initialValue = HomeUiState()
//            )

    init {
        viewModelScope.launch {
            authState = userRepo.getAuthState()
                .stateIn(
                    scope = viewModelScope,
                    started = SharingStarted.WhileSubscribed(5000L),
                    initialValue = userRepo.getAppUser()
                )
            homeUiState = wallpapersRepo.getWallpapers(sourceName, authState.value.id)
                .map { HomeUiState(itemList = it) }
                .stateIn(
                    scope = viewModelScope,
                    started = SharingStarted.WhileSubscribed(5000L),
                    initialValue = HomeUiState()
                )
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