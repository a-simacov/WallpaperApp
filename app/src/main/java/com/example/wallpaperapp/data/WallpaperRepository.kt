package com.example.wallpaperapp.data

import kotlinx.coroutines.flow.MutableStateFlow

class WallpaperRepository() {

    private val remoteDataSource = RemoteDataSource()

    fun update(wallpapers: MutableStateFlow<List<Wallpaper>>) {
        return remoteDataSource.updateWallpapers(wallpapers)
    }

    suspend fun add(wallpaper: Wallpaper) {
        remoteDataSource.addWallpaper(wallpaper)
    }

}