package com.example.wallpaperapp.data

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "wallpapers")
data class Wallpaper(

    @PrimaryKey(autoGenerate = false)
    var id: String = "",

    @ColumnInfo(name = "name")
    var name: String = "",

    @ColumnInfo(name = "imgUrl")
    var imgUrl: String = "",

    @ColumnInfo(name = "authorId")
    var authorId: String = "",

    @ColumnInfo(name = "isFavourite")
    var isFavourite: MutableState<Boolean> = mutableStateOf(false)

)
