package com.example.wallpaperapp.screens

import android.app.WallpaperManager
import android.graphics.BitmapFactory
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.wallpaperapp.R
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.wallpaperapp.navigation.NavigationItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.net.URL

//@Preview
@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun WallpaperScreen(navController: NavHostController, url: String) {

    val context = LocalContext.current
    val wallpaperManager = WallpaperManager.getInstance(context)
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
    ) {
        GlideImage(
            model = url,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            contentDescription = "wallpaper",
            contentScale = ContentScale.FillHeight,
        )
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp),
            onClick = {
                coroutineScope.launch {
                    val task = async(Dispatchers.IO) {
                        BitmapFactory.decodeStream(
                            URL(url).openConnection().getInputStream()
                        )
                    }
                    val bitmap = task.await()
                    wallpaperManager.setBitmap(bitmap)
                }
                navController.popBackStack(NavigationItem.Home.route, inclusive = false)
            }
        ) {
            Text(
                text = "Set wallpaper",
                fontFamily = FontFamily(Font(R.font.raleway_bold)),
                fontSize = 24.sp,
            )
        }
    }

}