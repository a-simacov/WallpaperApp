package com.example.wallpaperapp.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.wallpaperapp.R
import com.example.wallpaperapp.screens.common.AppViewModelProvider
import com.example.wallpaperapp.screens.common.WallpapersCommonScreen

@Composable
fun DownloadsScreen(navController: NavHostController) {

    WallpapersCommonScreen(
        navController = navController,
        showAddButton = false,
        headerText = stringResource(id = R.string.downloads),
        vm = viewModel(factory = AppViewModelProvider.provide())
    )

}