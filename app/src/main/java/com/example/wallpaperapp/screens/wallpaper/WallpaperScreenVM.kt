package com.example.wallpaperapp.screens.wallpaper

import android.app.WallpaperManager
import android.content.Context
import android.graphics.BitmapFactory
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wallpaperapp.tools.DataHandler
import com.example.wallpaperapp.tools.safeCall
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.net.URL


class WallpaperScreenVM : ViewModel() {

    private val _setWallpaperState: MutableStateFlow<DataHandler<Boolean>> = MutableStateFlow(
        DataHandler.IDLE()
    )
    val setWallpaperState: StateFlow<DataHandler<Boolean>> = _setWallpaperState

    fun setSystemWallpaper(context: Context, url: String) {
        val wallpaperManager = WallpaperManager.getInstance(context)

        viewModelScope.launch {
            _setWallpaperState.update { DataHandler.LOADING() }
            val handler = safeCall {
                val task = async(Dispatchers.IO) {
                    BitmapFactory.decodeStream(
                        URL(url).openConnection().getInputStream()
                    )
                }
                val bitmap = task.await()
                wallpaperManager.setBitmap(bitmap)
                DataHandler.SUCCESS(true)
            }
            _setWallpaperState.update { handler }
        }
    }

}