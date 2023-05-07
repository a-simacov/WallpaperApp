package com.example.wallpaperapp.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class BCReceiverRepoError : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val info = intent.getStringExtra("error_info")!!
        Toast.makeText(context, info, Toast.LENGTH_LONG).show()
    }
}