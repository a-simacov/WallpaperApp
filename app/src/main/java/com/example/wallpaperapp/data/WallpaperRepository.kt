package com.example.wallpaperapp.data

import android.net.Uri
import com.example.wallpaperapp.tools.DataHandler
import com.example.wallpaperapp.tools.safeCall
import kotlinx.coroutines.flow.Flow
import java.net.URI

class WallpaperRepository(private val dao: Dao) {

    private val remoteDataSource = RemoteDataSource()

    fun getWallpapers(sourceName: String, userId: String): Flow<List<Wallpaper>> {
        return when (sourceName) {
            "DOWNLOADS" -> dao.getWallpapers()
            "FAVOURITES" -> remoteDataSource.getFavsFlow(userId)
            else -> remoteDataSource.getListFlow(userId)
        }
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

    suspend fun getWallpaper(id: String): Wallpaper {
        return remoteDataSource.get(id)
    }

    suspend fun saveLocal(wallpaper: Wallpaper, uri: URI) {
        wallpaper.imgUrl = uri.toString()
        dao.insertWallpaper(wallpaper)
    }

}