package com.example.wallpaperapp.data

data class Wallpaper(
    val id: String = "",
    val name: String = "",
    val imgUrl: String = "",
    val userAdded: String = "",
    var isFavourite: Boolean = false
)
