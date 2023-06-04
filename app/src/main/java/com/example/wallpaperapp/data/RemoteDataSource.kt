package com.example.wallpaperapp.data

import android.net.Uri
import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.ProducerScope
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.util.UUID

class RemoteDataSource {

    private val tag = "FIREBASE"
    private val database = Firebase.database(
        "https://wallpaperapp-d3bc3-default-rtdb.europe-west1.firebasedatabase.app"
    )

    fun getListFlow(userId: String): Flow<List<Wallpaper>> {
        val refWallpapers = database.getReference("wallpapers")
        val refFavs = database.getReference("favourites/$userId")
        var allWallpapers = mutableListOf<Wallpaper>()

        return callbackFlow {
            val listenerWallpapers = newListenerWallpapers(this, allWallpapers, userId)
            val listenerFavs = newListenerFavs(this, allWallpapers)
            refWallpapers.addValueEventListener(listenerWallpapers)
            refFavs.addValueEventListener(listenerFavs)
            awaitClose {
                refWallpapers.removeEventListener(listenerWallpapers)
                refFavs.removeEventListener(listenerFavs)
            }
        }
    }

    private fun newListenerWallpapers(
        producerScope: ProducerScope<List<Wallpaper>>,
        allWallpapers: MutableList<Wallpaper>,
        userId: String
    ): ValueEventListener {
        return object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                allWallpapers.addAll(
                    snapshot.getValue<HashMap<String, Wallpaper>>()!!.values.toMutableList()
                )

                CoroutineScope(Dispatchers.IO).launch {
                    val favRef = database.getReference("favourites/$userId")
                    updateFavs(favRef.get().await(), allWallpapers)
                    producerScope.trySend(allWallpapers)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                producerScope.close(error.toException())
            }
        }
    }

    private fun newListenerFavs(
        producerScope: ProducerScope<List<Wallpaper>>,
        allWallpapers: MutableList<Wallpaper>
    ): ValueEventListener {
        return object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                updateFavs(snapshot, allWallpapers)
                producerScope.trySend(allWallpapers)
            }

            override fun onCancelled(error: DatabaseError) {
                producerScope.close(error.toException())
            }
        }
    }

    fun getFavsFlow(userId: String): Flow<List<Wallpaper>> {
        val ref = database.getReference("favourites/$userId")
        return callbackFlow {
            val listener = object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val favIds = getFavouriteIds(snapshot)
                    CoroutineScope(Dispatchers.IO).launch {
                        val dbRef = database.getReference("wallpapers")
                        val favWallpapers = mutableListOf<Wallpaper>()
                        for (favId in favIds) {
                            dbRef.child(favId).get().await().getValue<Wallpaper>()?.also {
                                it.isFavourite.value = true
                                favWallpapers.add(it)
                            }
                        }
                        trySend(favWallpapers)
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    close(error.toException())
                }
            }
            ref.addValueEventListener(listener)
            awaitClose { ref.removeEventListener(listener) }
        }
    }

    private fun updateFavs(snapshot: DataSnapshot, allWallpapers: MutableList<Wallpaper>) {
        val favIds = getFavouriteIds(snapshot)
        allWallpapers.onEach { wallpaper ->
            wallpaper.isFavourite.value = (wallpaper.id in favIds)
        }
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

    suspend fun get(id: String): Wallpaper {
        val dbRef = database.getReference("wallpapers")
        return dbRef.child(id).get().await().getValue<Wallpaper>()!!
    }

}
