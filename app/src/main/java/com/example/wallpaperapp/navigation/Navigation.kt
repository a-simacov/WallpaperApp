package com.example.wallpaperapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.wallpaperapp.screens.auth.PreAuthScreen
import com.example.wallpaperapp.screens.auth.SignInScreen
import com.example.wallpaperapp.screens.auth.SignUpScreen
import com.example.wallpaperapp.screens.newwallpaper.AddWallpaperScreen
import com.example.wallpaperapp.screens.bottom.DownloadsScreen
import com.example.wallpaperapp.screens.bottom.FavouritesScreen
import com.example.wallpaperapp.screens.bottom.HomeScreen
import com.example.wallpaperapp.screens.wallpaper.WallpaperScreen

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
                navArgument(ID_KEY) {
                    type = NavType.StringType
                    defaultValue = ""
                },
            )
        ) {
            WallpaperScreen(
                navController,
                it.arguments?.getString(ID_KEY)!!.toString(),
            )
        }
        composable(NavigationItem.NewWallpaper.route) {
            AddWallpaperScreen(navController)
        }
        composable(NavigationItem.PreAuth.route) {
            PreAuthScreen(navController)
        }
        composable(NavigationItem.SignUp.route) {
            SignUpScreen(navController)
        }
        composable(NavigationItem.SignIn.route) {
            SignInScreen(navController)
        }
    }

}