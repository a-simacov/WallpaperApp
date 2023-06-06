package com.example.wallpaperapp.user

import android.content.Context
import com.example.wallpaperapp.App
import com.example.wallpaperapp.tools.DataHandler
import com.example.wallpaperapp.tools.safeCall

class UserRepository(private val context: Context) {

    private val firebaseStorage = FirebaseAppUserStorage(context)

    suspend fun getAppUser(): User {
        return firebaseStorage.getUser()
    }

    suspend fun setAppUser(email: String, pass: String): DataHandler<User> {
        return safeCall {
            firebaseStorage.signIn(email, pass)
            val user = getAppUser().also { user ->
                (context as App).setUser(user)
            }
            DataHandler.SUCCESS(user)
        }
    }

    suspend fun newAppUser(email: String, pass: String): DataHandler<User> {
        return safeCall {
            firebaseStorage.signUp(email, pass)
            val user = getAppUser().also { user ->
                (context as App).setUser(user)
            }
            DataHandler.SUCCESS(user)
        }
    }

    suspend fun signOut() {
        firebaseStorage.signOut()
        firebaseStorage.getUser()
    }

}