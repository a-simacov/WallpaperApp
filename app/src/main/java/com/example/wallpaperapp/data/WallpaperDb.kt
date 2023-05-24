package com.example.wallpaperapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [WallpaperLocal::class], version = 1)
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