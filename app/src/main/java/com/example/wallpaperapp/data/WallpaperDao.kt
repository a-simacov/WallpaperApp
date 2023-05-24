package com.example.wallpaperapp.data

import androidx.lifecycle.LiveData
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@androidx.room.Dao
interface Dao {

    @Insert
    suspend fun insertWallpaper(wallpaper: WallpaperLocal)

    @Delete
    suspend fun deleteWallpaper(wallpaper: WallpaperLocal)

    @Query("SELECT * FROM wallpapers")
    fun getWallpapers(): LiveData<MutableList<WallpaperLocal>>

}