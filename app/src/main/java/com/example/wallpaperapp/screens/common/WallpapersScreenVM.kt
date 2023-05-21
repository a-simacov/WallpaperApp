package com.example.wallpaperapp.screens.common

import android.util.Log
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

class WallpapersScreenVM(private val dbname: String) : ViewModel() {

    private var pWallpapers = MutableStateFlow(listOf<Wallpaper>())
    val wallpapers: StateFlow<List<Wallpaper>> = pWallpapers.asStateFlow()

    private val wallpapersRepo = WallpaperRepository()
    private val userId = Firebase.auth.currentUser?.uid ?: ""

    init {
        updateWallpapers()
        Log.d("VMINIT", dbname)
    }

    private fun updateWallpapers() {
        wallpapersRepo.update(pWallpapers, userId, dbname)
    }

    fun changeFav(wallpaper: Wallpaper) {
        viewModelScope.launch {
            wallpapersRepo.changeFavourite(wallpaper, userId)
        }
    }

}