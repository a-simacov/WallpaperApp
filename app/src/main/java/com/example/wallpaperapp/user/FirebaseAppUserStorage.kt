package com.example.wallpaperapp.user

import android.content.Context
import com.example.wallpaperapp.tools.Constants
import com.example.wallpaperapp.tools.sendLocalBroadcastError
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class FirebaseAppUserStorage(private val context: Context) {

    private val auth = Firebase.auth

    suspend fun getUser(): User {
        if (auth.currentUser == null)
            try {
                auth.signInAnonymously().await()
            } catch (e: Exception) {
                sendLocalBroadcastError(context, Constants.LACTION_REPO_ERROR, e.message!!)
            }
        return auth.currentUser?.let {
            User(id = it.uid, name = it.email ?: "", isAuth = !it.isAnonymous)
        } ?: User()
    }

    suspend fun signUp(email: String, pass: String) {
        try {
            auth.createUserWithEmailAndPassword(email, pass).await()
        } catch (e: Exception) {
            sendLocalBroadcastError(context, Constants.LACTION_REPO_ERROR, e.message!!)
        }
    }

    suspend fun signIn(email: String, pass: String) {
        try {
            auth.signInWithEmailAndPassword(email, pass).await()
        } catch (e: Exception) {
            sendLocalBroadcastError(context, Constants.LACTION_REPO_ERROR, e.message!!)
        }
    }

    fun signOut() {
        auth.signOut()
    }

}