package com.example.wallpaperapp.screens

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.wallpaperapp.data.Wallpaper
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class HomeScreenViewModel(application: Application) : AndroidViewModel(application) {

    private val pWallpapers = MutableStateFlow(mutableListOf<Wallpaper>())
    val wallpapers: StateFlow<MutableList<Wallpaper>>
        get() = pWallpapers

    init {
        viewModelScope.launch {
            try {
                val reference = Firebase.storage.reference
                val images = reference.child("wallpapers/").listAll().await()
                images.items.forEach {
                    val url = it.downloadUrl.await().toString()
                    pWallpapers.value.add(
                        Wallpaper(it.name, url)
                    )
                }
            } catch (e: Exception) {
                Toast.makeText(application.applicationContext, e.message, Toast.LENGTH_LONG).show()
            }
        }
    }

}