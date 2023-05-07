package com.example.wallpaperapp.screens

import androidx.compose.foundation.Image
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wallpaperapp.R

@Preview
@Composable
fun WallpaperScreen() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
    ) {
        Image(
            painter = painterResource(id = R.drawable.wallpaper_icon),
            contentDescription = "wallpaper",
            modifier = Modifier.fillMaxSize().weight(1f),
            contentScale = ContentScale.Fit
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