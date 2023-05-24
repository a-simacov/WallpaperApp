package com.example.wallpaperapp.data

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

data class Wallpaper(
    var id: String = "",
    var name: String = "",
    var imgUrl: String = "",
    var authorId: String = "",
    var isFavourite: MutableState<Boolean> = mutableStateOf(false)
)
