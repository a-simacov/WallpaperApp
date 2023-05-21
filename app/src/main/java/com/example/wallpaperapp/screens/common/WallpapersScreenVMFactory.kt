package com.example.wallpaperapp.screens.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class WallpapersScreenVMFactory(private val sourceName: String) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T = WallpapersScreenVM(sourceName) as T
}