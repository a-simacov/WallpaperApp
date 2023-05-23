package com.example.wallpaperapp.data

import android.net.Uri
import com.example.wallpaperapp.tools.DataHandler
import com.example.wallpaperapp.tools.safeCall
import kotlinx.coroutines.flow.MutableStateFlow

class WallpaperRepository() {

    private val remoteDataSource = RemoteDataSource()

    fun update(wallpapers: MutableStateFlow<DataHandler<List<Wallpaper>>>, userId: String, sourceName: String) {
        remoteDataSource.updateWallpapers(wallpapers, userId, sourceName)
    }

    suspend fun add(wallpaper: Wallpaper, imgLocalUri: Uri): DataHandler<Wallpaper> {
        return safeCall {
            remoteDataSource.addWallpaper(wallpaper, imgLocalUri)
            DataHandler.SUCCESS(wallpaper)
        }
    }

    suspend fun changeFavourite(wallpaper: Wallpaper, userId: String) {
        if (wallpaper.isFavourite.value) {
            remoteDataSource.addToFav(wallpaper, userId)
        } else {
            remoteDataSource.deleteFromFav(wallpaper, userId)
        }
    }

}