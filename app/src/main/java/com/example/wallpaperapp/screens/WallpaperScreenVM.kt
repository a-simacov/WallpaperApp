package com.example.wallpaperapp.screens

import android.app.WallpaperManager
import android.content.Context
import android.graphics.BitmapFactory
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.net.URL


class WallpaperScreenVM : ViewModel() {

    private var pOpExecuted = MutableStateFlow(false)
    val opExecuted: StateFlow<Boolean> = pOpExecuted.asStateFlow()

    fun setSystemWallpaper(context: Context, url: String) {
        val wallpaperManager = WallpaperManager.getInstance(context)

        viewModelScope.launch {
            val task = async(Dispatchers.IO) {
                BitmapFactory.decodeStream(
                    URL(url).openConnection().getInputStream()
                )
            }
            val bitmap = task.await()
            wallpaperManager.setBitmap(bitmap)
            pOpExecuted.update { true }
        }
    }

}