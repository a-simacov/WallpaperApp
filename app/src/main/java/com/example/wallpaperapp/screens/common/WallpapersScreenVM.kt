package com.example.wallpaperapp.screens.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wallpaperapp.data.Wallpaper
import com.example.wallpaperapp.data.WallpaperRepository
import com.example.wallpaperapp.tools.DataHandler
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class WallpapersScreenVM(private val sourceName: String, private val wallpapersRepo: WallpaperRepository) : ViewModel() {

    private val _wallpapersDataHandler: MutableStateFlow<DataHandler<List<Wallpaper>>> =
        MutableStateFlow(
            DataHandler.IDLE()
        )
    val wallpapersDataHandler: StateFlow<DataHandler<List<Wallpaper>>> = _wallpapersDataHandler

    private val userId = Firebase.auth.currentUser?.uid ?: ""

    init {
        updateWallpapers()
    }

    private fun updateWallpapers() {
        _wallpapersDataHandler.update { DataHandler.LOADING() }
        viewModelScope.launch {
            wallpapersRepo.update(_wallpapersDataHandler, userId, sourceName)
        }
    }

    fun changeFav(wallpaper: Wallpaper) {
        viewModelScope.launch {
            wallpapersRepo.changeFavourite(wallpaper, userId)
        }
    }

}