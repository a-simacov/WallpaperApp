package com.example.wallpaperapp.screens

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wallpaperapp.data.Wallpaper
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.util.UUID

class AddWallpaperScreenViewModel : ViewModel() {

    fun saveImage(imageName: String, imageUri: Uri) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val tag = "FIREBASE"
                val uid = UUID.randomUUID().toString()
                val fileRef = Firebase.storage.reference.child("wallpapers/$uid")

                Log.d(tag, "start putfile")
                fileRef.putFile(imageUri).await()

                Log.d(tag, "start downloadUrl")
                val imgUrl = fileRef
                    .downloadUrl
                    .await()
                    .toString()

                val wallpaper = Wallpaper(
                    name = imageName,
                    imgUrl = imgUrl,
                    id = uid
                )

                val database = Firebase.database(
                    "https://wallpaperapp-d3bc3-default-rtdb.europe-west1.firebasedatabase.app"
                )
                val dbRef = database.getReference("wallpapers")
                dbRef.child(uid).setValue(wallpaper)
            } catch (e: Exception) {
                //Toast.makeText(application.applicationContext, e.message, Toast.LENGTH_LONG).show()
            }
        }
    }


}