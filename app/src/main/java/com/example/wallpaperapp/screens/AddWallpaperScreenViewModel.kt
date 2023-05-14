package com.example.wallpaperapp.screens

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.wallpaperapp.data.Wallpaper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AddWallpaperScreenViewModel : AndroidViewModel(Application()) {

    private var pOpExecuted = MutableStateFlow(false)
    val opExecuted: StateFlow<Boolean> = pOpExecuted.asStateFlow()

//    lateinit var wallpaperRepo: WallpaperRepository

    fun saveImage(wallpaper: Wallpaper) {
        viewModelScope.launch {
            //wallpaper.id = Firebase.auth.currentUser?.uid ?: ""
//            wallpaperRepo = WallpaperRepository()
//            wallpaperRepo.add(wallpaper)
            pOpExecuted.update { true }
        }
    }

}