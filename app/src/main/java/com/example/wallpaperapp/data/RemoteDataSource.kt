package com.example.wallpaperapp.data

import android.net.Uri
import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.tasks.await
import java.util.UUID

class RemoteDataSource {

    private val tag = "FIREBASE"
    private val database = Firebase.database(
        "https://wallpaperapp-d3bc3-default-rtdb.europe-west1.firebasedatabase.app"
    )
    // Если не указывать ссылку на RTDB, то получаю вот такое сообщение в логе.

    // pc_0 - Firebase Database connection was forcefully killed by the server.
    // Will not attempt reconnect. Reason: Database lives in a different region.
    // Please change your database URL to https://wallpaperapp-d3bc3-default-rtdb.europe-west1.firebasedatabase.app

    fun updateWallpapers(wallpapers: MutableStateFlow<List<Wallpaper>>) {
        database.getReference("wallpapers").addValueEventListener(
            object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if (dataSnapshot.exists()) {
                        wallpapers.update {
                            dataSnapshot.getValue<HashMap<String, Wallpaper>>()!!.values.toList()
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.w("RTDB ERROR", "Failed to read value.", error.toException())
                }
            }
        )
    }

    suspend fun addToFav(wallpaper: Wallpaper, userId: String) {
        try {
            val dbRef = database.getReference("favourites")
            dbRef.child(userId).push().setValue(wallpaper).await()
        } catch (e: Exception) {
            Log.d(tag, "Error setValue: ${e.message!!}")
        }
    }

    suspend fun deleteFromFav(wallpaper: Wallpaper, userId: String) {
        try {
            val dbRef = database.getReference("favourites")
            val query = dbRef.child(userId).orderByChild("id").equalTo(wallpaper.id)
            val found = query.get().await()
            if (found.exists())
                found.ref.removeValue().await()
        } catch (e: Exception) {
            Log.d(tag, "Error setValue: ${e.message!!}")
        }
    }

    suspend fun addWallpaper(wallpaper: Wallpaper, imgLocalUri: Uri) {
        try {
            val uid = UUID.randomUUID().toString()
            val fileRef = Firebase.storage.reference.child("wallpapers/$uid")

            putFile(fileRef, imgLocalUri)
            wallpaper.imgUrl = getDownloadUrl(fileRef)
            wallpaper.id = uid

            saveImage( wallpaper )
        } catch (e: Exception) {
            //Toast.makeText(application.applicationContext, e.message, Toast.LENGTH_LONG).show()
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
