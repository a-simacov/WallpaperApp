package com.example.wallpaperapp.screens

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wallpaperapp.R
import java.net.URLDecoder
import coil.compose.AsyncImage
import coil.request.ImageRequest

//@Preview
//@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun WallpaperScreen(url: String) {

    Log.d("GLIDE-SCREEN", URLDecoder.decode(url, "UTF-8"))

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
    ) {
//        GlideImage(
//            model = URLDecoder.decode(url, "UTF-8"),
//            modifier = Modifier.fillMaxSize().weight(1f),
//            contentDescription = "wallpaper",
//            contentScale = ContentScale.Fit,
//        )
        AsyncImage(
            //model = URLDecoder.decode(url, "UTF-8"),
            model = ImageRequest.Builder(LocalContext.current)
                .data(URLDecoder.decode(url, "UTF-8"))
                .crossfade(true)
                .build(),
            modifier = Modifier.fillMaxSize().weight(1f),
            contentDescription = "wallpaper",
            contentScale = ContentScale.Fit,
        )
        Button(
            modifier = Modifier.fillMaxWidth().height(64.dp),
            onClick = { /*TODO*/ }
        ) {
            Text(
                text = "Set wallpaper",
                fontFamily = FontFamily(Font(R.font.raleway_bold)),
                fontSize = 24.sp,
            )
        }
    }

}