package com.example.wallpaperapp.screens

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wallpaperapp.data.Wallpaper
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.util.UUID

class AddWallpaperScreenViewModel : ViewModel() {

    private var pOpExecuted = MutableStateFlow(false)
    val opExecuted: StateFlow<Boolean> = pOpExecuted.asStateFlow()

    val database = Firebase.database(
        "https://wallpaperapp-d3bc3-default-rtdb.europe-west1.firebasedatabase.app"
    )
    private val tag = "FIREBASE"

    fun saveImage(imgName: String, imageUri: Uri) {
        viewModelScope.launch {
            try {
                val uid = UUID.randomUUID().toString()
                val fileRef = Firebase.storage.reference.child("wallpapers/$uid")

                putFile(fileRef, imageUri)
                val imgUrl = getDownloadUrl(fileRef)

                val wallpaper = Wallpaper(name = imgName, imgUrl = imgUrl, id = uid)
                saveImage(wallpaper)
            } catch (e: Exception) {
                //Toast.makeText(application.applicationContext, e.message, Toast.LENGTH_LONG).show()
            }
            pOpExecuted.update { true }
        }
    }

    private suspend fun putFile(fileRef: StorageReference, imageUri: Uri) {
        try {
            fileRef.putFile(imageUri).await()
        } catch (e: Exception) {
            Log.d(tag, "Error putFile: ${e.message!!}")
        }
    }

    private suspend fun getDownloadUrl(fileRef: StorageReference): String {
        return try {
            fileRef.downloadUrl.await().toString()
        } catch (e: Exception) {
            Log.d(tag, "Error downloadUrl: ${e.message!!}")
            ""
        }
    }

    private fun saveImage(wallpaper: Wallpaper) {
        try {
            val dbRef = database.getReference("wallpapers")
            dbRef.child(wallpaper.id).setValue(wallpaper)
        } catch (e: Exception) {
            Log.d(tag, "Error setValue: ${e.message!!}")
        }
    }

}