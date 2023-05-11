package com.example.wallpaperapp.screens

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wallpaperapp.data.Wallpaper
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class HomeScreenViewModel : ViewModel() {

    private var pWallpapers = MutableStateFlow(listOf<Wallpaper>())
    val wallpapers: StateFlow<List<Wallpaper>> = pWallpapers.asStateFlow()

    private val database =
        Firebase.database(
        "https://wallpaperapp-d3bc3-default-rtdb.europe-west1.firebasedatabase.app"
        )

    init {
        //getWallpapers()

//        viewModelScope.launch {
//            try {
//                val reference = Firebase.storage.reference
//                val images = reference.child("wallpapers/").listAll().await()
//                val list = mutableListOf<Wallpaper>()
//                images.items.forEach {
//                    val url = it.downloadUrl.await().toString()
//                    list.add(
//                        Wallpaper(name = it.name, imgUrl = url)
//                    )
//                }
//                pWallpapers.update { list }
//            } catch (e: Exception) {
//                //Toast.makeText(application.applicationContext, e.message, Toast.LENGTH_LONG).show()
//            }
//        }
    }

    private fun getWallpapers() {
        database.getReference("wallpapers")
            .addValueEventListener(
                object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        pWallpapers.update { dataSnapshot.getValue<List<Wallpaper>>()!! }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        Log.w("RTDB ERROR", "Failed to read value.", error.toException())
                    }
                }
            )
    }

}