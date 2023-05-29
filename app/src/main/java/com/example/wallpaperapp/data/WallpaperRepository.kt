package com.example.wallpaperapp.data

import android.net.Uri
import com.example.wallpaperapp.tools.DataHandler
import com.example.wallpaperapp.tools.safeCall
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import java.net.URI

class WallpaperRepository(private val dao: Dao) {

    private val remoteDataSource = RemoteDataSource()

    suspend fun update(wallpapers: MutableStateFlow<DataHandler<List<Wallpaper>>>, userId: String, sourceName: String) {
        when (sourceName) {
            "HOME" -> remoteDataSource.updateWallpapers(wallpapers, userId)
            "FAVOURITES" -> remoteDataSource.updateFavs(wallpapers, userId)
            "DOWNLOADS" -> wallpapers.update {
                safeCall {
                    DataHandler.SUCCESS(dao.getWallpapers())
                }
            }
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