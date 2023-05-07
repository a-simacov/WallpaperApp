package com.example.wallpaperapp.tools

import android.content.Context
import android.content.Intent
import androidx.localbroadcastmanager.content.LocalBroadcastManager

fun sendLocalBroadcastError(context: Context, action: String, error_info: String) {
    Intent().also {
        it.action = action
        it.putExtra("error_info", error_info)
        LocalBroadcastManager.getInstance(context).sendBroadcast(it)
    }
}
