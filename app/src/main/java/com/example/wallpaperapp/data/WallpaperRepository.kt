package com.example.wallpaperapp.data

import android.net.Uri
import kotlinx.coroutines.flow.MutableStateFlow

class WallpaperRepository() {

    private val remoteDataSource = RemoteDataSource()

    fun update(wallpapers: MutableStateFlow<List<Wallpaper>>, userId: String, sourceName: String) {
        remoteDataSource.updateWallpapers(wallpapers, userId, sourceName)
    }

    suspend fun add(wallpaper: Wallpaper, imgLocalUri: Uri) {
        remoteDataSource.addWallpaper(wallpaper, imgLocalUri)
    }

    suspend fun changeFavourite(wallpaper: Wallpaper, userId: String) {
        if (wallpaper.isFavourite.value) {
            remoteDataSource.addToFav(wallpaper, userId)
        } else {
            remoteDataSource.deleteFromFav(wallpaper, userId)
        }
    }

}