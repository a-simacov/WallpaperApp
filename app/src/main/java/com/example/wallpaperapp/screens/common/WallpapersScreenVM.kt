package com.example.wallpaperapp.screens.common

import androidx.lifecycle.ViewModel
import com.example.wallpaperapp.data.Wallpaper
import com.example.wallpaperapp.data.WallpaperRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

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

}