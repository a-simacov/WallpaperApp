package com.example.wallpaperapp.user

import android.content.Context
import com.example.wallpaperapp.App

class UserRepository(private val context: Context) {

    private val firebaseStorage = FirebaseAppUserStorage(context)
    private val defaultStorage = DefaultAppUserStorage()

    fun getAppUser(): User {
        return firebaseStorage.getUser() ?: defaultStorage.getUser()
    }

    suspend fun setAppUser(email: String, pass: String): User {
        firebaseStorage.signIn(email, pass)
        return getAppUser().also { user ->
            (context as App).setUser(user)
        }
    }

    suspend fun newAppUser(email: String, pass: String): User {
        firebaseStorage.signUp(email, pass)
        return getAppUser().also { user ->
            (context as App).setUser(user)
        }
    }

    suspend fun resetAppUser() {
        firebaseStorage.signOut()
        (context as App).setUser(
            defaultStorage.getUser()
        )
    }

}