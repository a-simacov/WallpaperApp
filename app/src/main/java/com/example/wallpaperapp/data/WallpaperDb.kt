package com.example.wallpaperapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [Wallpaper::class], version = 1)
@TypeConverters(Converters::class)
abstract class Db: RoomDatabase() {
    abstract fun getDao(): Dao

    companion object {
        fun getDb(context: Context): Db {
            return Room.databaseBuilder(
                context.applicationContext,
                Db::class.java,
                "wallpapers.db"
            ).fallbackToDestructiveMigration().build()
        }
    }
}