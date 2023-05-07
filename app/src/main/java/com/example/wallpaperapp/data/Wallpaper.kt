package com.example.wallpaperapp.data

data class Wallpaper(
    val name: String,
    val imgUrl: String,
    var isFavourite: Boolean = false
)
