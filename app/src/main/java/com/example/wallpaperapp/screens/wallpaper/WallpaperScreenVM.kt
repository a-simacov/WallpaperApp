package com.example.wallpaperapp.screens.wallpaper

import android.app.WallpaperManager
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Environment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wallpaperapp.data.Wallpaper
import com.example.wallpaperapp.data.WallpaperRepository
import com.example.wallpaperapp.tools.DataHandler
import com.example.wallpaperapp.tools.safeCall
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.net.URI
import java.net.URL


class WallpaperScreenVM(private val wallpaperRepo: WallpaperRepository) : ViewModel() {

    private val _setWallpaperState: MutableStateFlow<DataHandler<Boolean>> = MutableStateFlow(
        DataHandler.IDLE()
    )
    val setWallpaperState: StateFlow<DataHandler<Boolean>> = _setWallpaperState
    var wallpaper: MutableStateFlow<Wallpaper?> = MutableStateFlow(null)

    fun getWallpaper(id: String) {
        viewModelScope.launch {
            wallpaper.update { wallpaperRepo.getWallpaper(id) }
        }
    }

    fun setSystemWallpaper(context: Context) {
        val wallpaperManager = WallpaperManager.getInstance(context)

        viewModelScope.launch {
            _setWallpaperState.update { DataHandler.LOADING() }
            val handler = safeCall {
                val task = async(Dispatchers.IO) {
                    BitmapFactory.decodeStream(
                        URL(wallpaper.value?.imgUrl).openConnection().getInputStream()
                    )
                }
                val bitmap = task.await()
                wallpaperManager.setBitmap(bitmap)
                val imgUri = saveBitmap(bitmap)
                wallpaper.value?.let { wallpaperRepo.saveLocal(it, imgUri) }
                DataHandler.SUCCESS(true)
            }
            _setWallpaperState.update { handler }
        }
    }

    private fun saveBitmap(bitmap: Bitmap): URI {
        val filename = "${System.currentTimeMillis()}.jpg"
        val imagesDir =
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
        val image = File(imagesDir, filename)
        FileOutputStream(image).use {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, it)
        }
        return image.toURI()
    }

}