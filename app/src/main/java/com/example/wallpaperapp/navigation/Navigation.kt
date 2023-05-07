package com.example.wallpaperapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.wallpaperapp.screens.DownloadsScreen
import com.example.wallpaperapp.screens.FavouritesScreen
import com.example.wallpaperapp.screens.HomeScreen
import com.example.wallpaperapp.screens.WallpaperScreen

@Composable
fun Navigation(navController: NavHostController) {

    NavHost(navController = navController, startDestination = NavigationItem.Home.route) {
        composable(NavigationItem.Home.route) {
            HomeScreen()
        }
        composable(NavigationItem.Favourites.route) {
            FavouritesScreen()
        }
        composable(NavigationItem.Downloads.route) {
            DownloadsScreen()
        }
        composable(NavigationItem.Wallpaper.route) {
            WallpaperScreen()
        }
    }

}