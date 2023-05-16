package com.example.wallpaperapp.data

data class Wallpaper(
    var id: String = "",
    var name: String = "",
    var imgUrl: String = "",
    var authorId: String = "",
    var isFavourite: Boolean = false,
)
