package com.example.wallpaperapp.data

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.room.TypeConverter

class Converters {
    @TypeConverter
    fun fromBooleanMutableState(value: MutableState<Boolean>): Boolean {
        return value.value
    }

    @TypeConverter
    fun toBooleanMutableState(value: Boolean): MutableState<Boolean> {
        return mutableStateOf(value)
    }
}
