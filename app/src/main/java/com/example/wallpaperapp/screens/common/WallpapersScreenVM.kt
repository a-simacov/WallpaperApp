package com.example.wallpaperapp.screens.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wallpaperapp.data.Wallpaper
import com.example.wallpaperapp.data.WallpaperRepository
import com.example.wallpaperapp.user.User
import com.example.wallpaperapp.user.UserRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class WallpapersScreenVM(
    sourceName: String,
    private val wallpapersRepo: WallpaperRepository,
    private val userRepo: UserRepository
) : ViewModel() {

    lateinit var authState: MutableStateFlow<User>
    val homeUiState: MutableStateFlow<HomeUiState> = MutableStateFlow(HomeUiState())

    init {
        viewModelScope.launch {
            authState = MutableStateFlow(userRepo.getAppUser())
            val auth = Firebase.auth
            val authStateListener = FirebaseAuth.AuthStateListener {
                CoroutineScope(Dispatchers.IO).launch {
                    authState.emit(userRepo.getAppUser())
                }
            }
            auth.addAuthStateListener(authStateListener)
            authState.collect { user ->
                homeUiState.emit(
                    HomeUiState(itemList = wallpapersRepo.getWallpapers(sourceName, user.id))
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