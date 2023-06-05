package com.example.wallpaperapp.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.wallpaperapp.R
import com.example.wallpaperapp.navigation.BottomNavigationBar
import com.example.wallpaperapp.navigation.Navigation
import com.example.wallpaperapp.navigation.NavigationItem

@Composable
fun MainScreen() {

    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            if (showBottomBar(navController = navController))
                BottomNavigationBar(navController)
        },
        content = { padding ->
            Box(modifier = Modifier.padding(padding)) {
                Navigation(navController = navController)
            }
        },
        // Set background color to avoid the white flashing when you switch between screens
        backgroundColor = colorResource(R.color.main_theme),
    )

}

@Composable
fun showBottomBar(navController: NavHostController): Boolean {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val route = navBackStackEntry?.destination?.route.toString()
    val routesNoBottom = listOf<String>(
        NavigationItem.SignIn.route,
        NavigationItem.SignUp.route,
        NavigationItem.NewWallpaper.route,
        NavigationItem.PreAuth.route,
        NavigationItem.SingleWallpaper.route,
    )
    return route !in routesNoBottom
}

@Preview
@Composable
fun MainScreenPreview() {
    MainScreen()
}