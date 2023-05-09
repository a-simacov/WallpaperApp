package com.example.wallpaperapp.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wallpaperapp.R

@Composable
fun LoadingScreen() {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.main_theme)),
        contentAlignment = Alignment.Center
    ) {
        Row(
            verticalAlignment = Alignment.Bottom
        ) {
            Text(
                color = Color.White,
                text = stringResource(id = R.string.wallpaper),
                fontSize = 48.sp,
                fontFamily = FontFamily(Font(R.font.raleway_bold))
            )
            Image(
                modifier = Modifier.size(width = 48.dp, height = 48.dp),
                painter = painterResource(id = R.drawable.splash_icon),
                contentDescription = "splash icon",
                contentScale = ContentScale.FillWidth,
            )
        }
    }

}

@Preview
@Composable
fun LoadingScreenPreview() {
    LoadingScreen()
}