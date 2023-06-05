package com.example.wallpaperapp.navigation

import com.example.wallpaperapp.R
import com.example.wallpaperapp.data.Wallpaper
import java.net.URLEncoder

const val ID_KEY = "id"

sealed class NavigationItem(var route: String, var icon: Int, var title: String) {

    object Home : NavigationItem("home", R.drawable.home_icon, "Home")
    object Favourites : NavigationItem("favourites", R.drawable.heart_icon, "Liked")
    object Downloads : NavigationItem("downloads", R.drawable.download_icon, "Downloads")
    object SingleWallpaper : NavigationItem(
        "wallpaper?$ID_KEY={$ID_KEY}",
        R.drawable.wallpaper_icon,
        "Wallpaper"
    ) {
        fun passUrl(wallpaper: Wallpaper): String {
            return this.route
                .replace("{$ID_KEY}", URLEncoder.encode(wallpaper.id, "UTF-8"))
        }
    }

    object NewWallpaper :
        NavigationItem("new_wallpaper", R.drawable.wallpaper_icon, "New wallpaper")

    object PreAuth :
        NavigationItem("pre_auth", R.drawable.wallpaper_icon, "Continue auth")

    object SignIn :
        NavigationItem("sign_in", R.drawable.wallpaper_icon, "Sign in")

    object SignUp :
        NavigationItem("sign_up", R.drawable.wallpaper_icon, "Sign up")

}
