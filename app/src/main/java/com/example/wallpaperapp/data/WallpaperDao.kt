package com.example.wallpaperapp.data

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@androidx.room.Dao
interface Dao {

    @Insert
    suspend fun insertWallpaper(wallpaper: Wallpaper)

    @Delete
    suspend fun deleteWallpaper(wallpaper: Wallpaper)

    @Query("SELECT * FROM wallpapers")
    suspend fun getWallpapers(): List<Wallpaper>

}