package com.example.wallpaperapp.screens

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wallpaperapp.data.Wallpaper
import com.example.wallpaperapp.data.WallpaperRepository
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AddWallpaperScreenViewModel : ViewModel() {

    private var pOpExecuted = MutableStateFlow(false)
    val opExecuted: StateFlow<Boolean> = pOpExecuted.asStateFlow()

    val wallpaperRepo = WallpaperRepository()

    fun saveImage(imgNameState: String, imgLocalUri: Uri) {
        viewModelScope.launch {
            val wallpaper = Wallpaper(
                name = imgNameState,
                authorId = Firebase.auth.currentUser?.uid ?: ""
            )
            wallpaperRepo.add(wallpaper, imgLocalUri)
            pOpExecuted.update { true }
        }
    }

}