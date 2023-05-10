package com.example.wallpaperapp.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wallpaperapp.data.Wallpaper
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class HomeScreenViewModel : ViewModel() {

    private var pWallpapers = MutableStateFlow(mutableListOf<Wallpaper>())
    val wallpapers: StateFlow<MutableList<Wallpaper>> = pWallpapers.asStateFlow()

    init {
        viewModelScope.launch {
            try {
                val reference = Firebase.storage.reference
                val images = reference.child("wallpapers/").listAll().await()
                val list = mutableListOf<Wallpaper>()
                images.items.forEach {
                    val url = it.downloadUrl.await().toString()
                    list.add(
                        Wallpaper(it.name, url)
                    )
                }
                pWallpapers.update { list }
            } catch (e: Exception) {
                //Toast.makeText(application.applicationContext, e.message, Toast.LENGTH_LONG).show()
            }
        }
    }

}