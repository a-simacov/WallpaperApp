package com.example.wallpaperapp.screens.common

import android.app.Application
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.wallpaperapp.App
import com.example.wallpaperapp.screens.newwallpaper.AddWallpaperScreenViewModel
import com.example.wallpaperapp.screens.wallpaper.WallpaperScreenVM

/**
 * Provides Factory to create instance of ViewModel for the entire Inventory app
 */
object AppViewModelProvider {
    fun provide(sourceName: String = ""): ViewModelProvider.Factory {
        return viewModelFactory {
            initializer {
                AddWallpaperScreenViewModel(app().wallpaperRepository)
            }
            initializer {
                WallpapersScreenVM(sourceName, app().wallpaperRepository)//app().wallpaperRepository)
            }
            initializer {
                WallpaperScreenVM()
            }
        }
    }
//    val Factory = viewModelFactory {
//        initializer {
//            AddWallpaperScreenViewModel(app().wallpaperRepository)
//        }
//        initializer {
//            WallpapersScreenVM(app().wallpaperRepository)//app().wallpaperRepository)
//        }
//        initializer {
//            WallpaperScreenVM()
//        }
//    }
}

/**
 * Extension function to queries for [Application] object and returns an instance of
 * [InventoryApplication].
 */
fun CreationExtras.app(): App =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as App)
