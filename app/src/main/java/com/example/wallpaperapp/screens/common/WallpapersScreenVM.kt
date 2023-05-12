package com.example.wallpaperapp.screens.common

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.wallpaperapp.data.Wallpaper
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class WallpapersScreenVM : ViewModel() {

    private var pWallpapers = MutableStateFlow(listOf<Wallpaper>())
    val wallpapers: StateFlow<List<Wallpaper>> = pWallpapers.asStateFlow()

    private val database =
        Firebase.database(
            "https://wallpaperapp-d3bc3-default-rtdb.europe-west1.firebasedatabase.app"
        )

    init {
        getWallpapers()
    }

    private fun getWallpapers() {
        database.getReference("wallpapers").addValueEventListener(
            object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if (dataSnapshot.exists()) {
                        pWallpapers.update {
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

}