package com.example.wallpaperapp.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.wallpaperapp.R
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.wallpaperapp.navigation.NavigationItem
import com.example.wallpaperapp.screens.common.ShowProgress

@Composable
fun WallpaperScreen(
    navController: NavHostController,
    url: String,
    wallpaperScreenVM: WallpaperScreenVM = viewModel()
) {

    val context = LocalContext.current

    val opExecuted by wallpaperScreenVM.opExecuted.collectAsState()
    if (opExecuted)
        navController.popBackStack(NavigationItem.Home.route, inclusive = false)

    var inProcess by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
    ) {
        if (inProcess)
            ShowProgress("Setting wallpaper...")
        else
            SetWallpaper(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                url = url,
                onClick = {
                    inProcess = true
                    wallpaperScreenVM.setSystemWallpaper(context = context, url = url)
                },
            )
    }

}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun SetWallpaper(
    modifier: Modifier = Modifier,
    url: String,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
    ) {
        GlideImage(
            model = url,
            modifier = Modifier.fillMaxSize(),
            contentDescription = "wallpaper",
            contentScale = ContentScale.FillHeight,
        )
    }
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp),
        onClick = onClick
    ) {
        Text(
            text = "Set wallpaper",
            fontFamily = FontFamily(Font(R.font.raleway_bold)),
            fontSize = 24.sp,
        )
    }
}