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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
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

    fun updateWallpapers(
        wallpapers: MutableStateFlow<List<Wallpaper>>,
        userId: String,
        sourceName: String
    ) {

        when (sourceName) {
            "HOME" -> initWallpapersListener(wallpapers, userId)
            "FAVOURITES" -> initFavsFilteredListener(wallpapers, userId)
            "DOWNLOADS" -> TODO("NOT IMPLEMENTED, AWAITING ROOM DB")
        }

    }

    private fun initWallpapersListener(
        wallpapers: MutableStateFlow<List<Wallpaper>>,
        userId: String
    ) {

        database.getReference("wallpapers").addValueEventListener(
            object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if (dataSnapshot.exists()) {
                        wallpapers.update {
                            dataSnapshot.getValue<HashMap<String, Wallpaper>>()!!.values.toList()
                        }
                        initFavsListener(wallpapers, userId)
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.d("RTDB ERROR", "Failed to read value.", error.toException())
                }
            }
        )

    }

    private fun initFavsListener(wallpapers: MutableStateFlow<List<Wallpaper>>, userId: String) {
        database.getReference("favourites/$userId").addValueEventListener(
            object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val favs = getFavouriteIds(dataSnapshot)
                    wallpapers.update { list ->
                        list.onEach { wallpaper ->
                            wallpaper.isFavourite.value = (wallpaper.id in favs)
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.d("RTDB ERROR", "Failed to read value.", error.toException())
                }
            }
        )
    }

    private fun initFavsFilteredListener(wallpapers: MutableStateFlow<List<Wallpaper>>, userId: String) {
        database.getReference("favourites/$userId").addValueEventListener(
            object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val favs = getFavouriteIds(dataSnapshot)
                    CoroutineScope(Dispatchers.IO).launch {
                        val dbRef = database.getReference("wallpapers")
                        val list = mutableListOf<Wallpaper>()
                        for (fav in favs) {
                            val res = dbRef.child(fav).get().await().getValue<Wallpaper>()
                            res?.also {
                                res.isFavourite.value = true
                                list.add(it)
                            }
                        }
                        wallpapers.update { list }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.d("RTDB ERROR", "Failed to read value.", error.toException())
                }
            }
        )
    }

    private fun getFavouriteIds(dataSnapshot: DataSnapshot): List<String> {
        return if (dataSnapshot.exists())
            dataSnapshot.getValue<HashMap<String, String>>()!!.values.toList()
        else
            emptyList()
    }

    suspend fun addToFav(wallpaper: Wallpaper, userId: String) {
        try {
            val dbRef = database.getReference("favourites")
            dbRef.child(userId).push().setValue(wallpaper.id).await()
        } catch (e: Exception) {
            Log.d(tag, "Error setValue: ${e.message!!}")
        }
    }

    suspend fun deleteFromFav(wallpaper: Wallpaper, userId: String) {
        try {
            val dbRef = database.getReference("favourites")
            val res = dbRef.child(userId).get().await()
            val list = res.children.toList()
            for (item in list) {
                if (wallpaper.id == item.value) {
                    item.ref.removeValue()
                    break
                }
            }
        } catch (e: Exception) {
            Log.d(tag, "Error setValue: ${e.message!!}")
        }
    }

    suspend fun addWallpaper(wallpaper: Wallpaper, imgLocalUri: Uri) {
        val storage = Firebase.storage
        storage.maxUploadRetryTimeMillis = 5000

        val uid = UUID.randomUUID().toString()
        val fileRef = storage.reference.child("wallpapers/$uid")

        fileRef.putFile(imgLocalUri).await()
        wallpaper.imgUrl = fileRef.downloadUrl.await().toString()
        wallpaper.id = uid

        writeWallpaper(wallpaper)
    }

    private fun writeWallpaper(wallpaper: Wallpaper) {
        val dbRef = database.getReference("wallpapers")
        dbRef.child(wallpaper.id).setValue(
            object {
                val id = wallpaper.id
                val name = wallpaper.name
                val imgUrl = wallpaper.imgUrl
                val authorId = wallpaper.authorId
            }
        )
    }

}
