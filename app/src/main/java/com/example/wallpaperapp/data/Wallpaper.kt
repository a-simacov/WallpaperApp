package com.example.wallpaperapp.data

import android.net.Uri

data class Wallpaper(
    var id: String = "",
    var name: String = "",
    var imgUrl: String = "",
    var authorId: String = "",
    var isFavourite: Boolean = false,
    var imgLocalUri: Uri? = null
)
