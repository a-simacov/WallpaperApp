package com.example.wallpaperapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.wallpaperapp.screens.AddWallpaperScreen
import com.example.wallpaperapp.screens.DownloadsScreen
import com.example.wallpaperapp.screens.FavouritesScreen
import com.example.wallpaperapp.screens.HomeScreen
import com.example.wallpaperapp.screens.WallpaperScreen

@Composable
fun Navigation(navController: NavHostController) {

    NavHost(navController = navController, startDestination = NavigationItem.Home.route) {
        composable(NavigationItem.Home.route) {
            HomeScreen(navController)
        }
        composable(NavigationItem.Favourites.route) {
            FavouritesScreen(navController)
        }
        composable(NavigationItem.Downloads.route) {
            DownloadsScreen(navController)
        }
        composable(
            route = NavigationItem.SingleWallpaper.route,
            arguments = listOf(
                navArgument(IMG_URL_KEY) {
                    type = NavType.StringType
                    defaultValue = ""
                },
            )
        ) {
            WallpaperScreen(
                navController,
                it.arguments?.getString(IMG_URL_KEY)!!.toString(),
            )
        }
        composable(NavigationItem.NewWallpaper.route) {
            AddWallpaperScreen(navController)
        }
    }

}