package com.example.wallpaperapp.navigation

import com.example.wallpaperapp.R

const val IMG_URL_KEY = "url"

sealed class NavigationItem(var route: String, var icon: Int, var title: String) {

    object Home : NavigationItem("home", R.drawable.home_icon, "Home")
    object Favourites : NavigationItem("favourites", R.drawable.heart_icon, "Liked")
    object Downloads : NavigationItem("downloads", R.drawable.download_icon, "Downloads")
    object Wallpaper : NavigationItem("wallpaper?$IMG_URL_KEY={$IMG_URL_KEY}", R.drawable.wallpaper_icon, "Wallpaper") {
        fun passUrl(url: String): String {
            return this.route.replace("{$IMG_URL_KEY}", url)
        }
    }
    object NewWallpaper : NavigationItem("new_wallpaper", R.drawable.wallpaper_icon, "New wallpaper")

}
