package com.example.wallpaperapp.screens.newwallpaper

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wallpaperapp.data.Wallpaper
import com.example.wallpaperapp.data.WallpaperRepository
import com.example.wallpaperapp.tools.DataHandler
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AddWallpaperScreenViewModel(private val wallpaperRepo: WallpaperRepository) : ViewModel() {

    private val _saveImgUiState: MutableStateFlow<DataHandler<Wallpaper>> = MutableStateFlow(DataHandler.IDLE())
    val saveImgUiState: StateFlow<DataHandler<Wallpaper>> = _saveImgUiState

    fun saveImage(imgNameState: String, imgLocalUri: Uri) {
        viewModelScope.launch {
            _saveImgUiState.update { DataHandler.LOADING() }
            val wallpaper = Wallpaper(
                name = imgNameState,
                authorId = Firebase.auth.currentUser?.uid ?: ""
            )
            _saveImgUiState.update { wallpaperRepo.add(wallpaper, imgLocalUri) }
        }
    }

}