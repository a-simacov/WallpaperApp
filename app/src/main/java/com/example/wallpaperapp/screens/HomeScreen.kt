package com.example.wallpaperapp.screens

import androidx.compose.runtime.*
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.wallpaperapp.R
import com.example.wallpaperapp.screens.common.WallpapersCommonScreen

@Composable
fun HomeScreen(navController: NavHostController) {

    WallpapersCommonScreen(
        navController = navController,
        showAddButton = true,
        headerText = stringResource(id = R.string.wallpaper)
    )

}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen(rememberNavController())
}