package com.example.wallpaperapp.user

import android.content.Context
import com.example.wallpaperapp.App
import com.example.wallpaperapp.tools.DataHandler
import com.example.wallpaperapp.tools.safeCall
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch

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

    fun getAuthState() = callbackFlow {
        val auth = Firebase.auth
        val authStateListener = FirebaseAuth.AuthStateListener {
            CoroutineScope(Dispatchers.IO).launch {
                trySend(firebaseStorage.getUser())
            }
        }
        auth.addAuthStateListener(authStateListener)
        awaitClose {
            auth.removeAuthStateListener(authStateListener)
        }
    }

}