package com.example.wallpaperapp.screens

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class AddWallpaperScreenViewModel : ViewModel() {
    fun saveImage(imageName: String, imageUri: Uri) {
        viewModelScope.launch {
            try {
                val reference = Firebase.storage.reference
                reference.child("wallpapers/$imageName")
                    .putFile(imageUri).await()
            } catch (e: Exception) {
                //Toast.makeText(application.applicationContext, e.message, Toast.LENGTH_LONG).show()
            }
        }
    }


}