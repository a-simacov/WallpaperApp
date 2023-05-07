package com.example.wallpaperapp.navigation

import com.example.wallpaperapp.R

sealed class NavigationItem(var route: String, var icon: Int, var title: String) {

    object Home : NavigationItem("home", R.drawable.home_icon, "Home")
    object Favourites : NavigationItem("favourites", R.drawable.heart_icon, "Liked")
    object Downloads : NavigationItem("downloads", R.drawable.download_icon, "Downloads")
    object Wallpaper : NavigationItem("wallpaper", R.drawable.wallpaper_icon, "Wallpaper")

}
