package com.example.wallpaperapp

import android.app.Application
import android.content.Context
import android.content.IntentFilter
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.wallpaperapp.data.Db
import com.example.wallpaperapp.data.WallpaperRepository
import com.example.wallpaperapp.receivers.BCReceiverRepoError
import com.example.wallpaperapp.tools.Constants
import com.example.wallpaperapp.user.User
import com.example.wallpaperapp.user.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class App : Application() {

    private lateinit var appUser: User
    private lateinit var userRepository: UserRepository
    lateinit var wallpaperRepository: WallpaperRepository

    override fun onCreate() {
        super.onCreate()
        userRepository = UserRepository(applicationContext)
        val dao = Db.getDb(applicationContext).getDao()
        wallpaperRepository = WallpaperRepository(dao)
        CoroutineScope(Dispatchers.IO).launch {
            appUser = userRepository.getAppUser()
        }

        registerBCReceivers(applicationContext)
    }

    private fun registerBCReceivers(context: Context) {
        LocalBroadcastManager.getInstance(context).also { bcManager ->
            bcManager.registerReceiver(
                BCReceiverRepoError(),
                IntentFilter(Constants.LACTION_REPO_ERROR)
            )
        }
    }

    fun getUser() = appUser

    fun setUser(user: User) {
        appUser = user
    }

    fun getUserRepo(): UserRepository = userRepository

}