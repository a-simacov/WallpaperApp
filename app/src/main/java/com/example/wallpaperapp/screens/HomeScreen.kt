package com.example.wallpaperapp.screens

import android.graphics.drawable.Icon
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wallpaperapp.R
import com.example.wallpaperapp.data.Wallpaper

fun getWallpapers(): List<Wallpaper> {

    return listOf(
        Wallpaper("name1", ""),
        Wallpaper("name2", ""),
        Wallpaper("name3", ""),
        Wallpaper("name4", ""),
        Wallpaper("name5", ""),
    )

}

@Composable
fun HomeScreen() {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.white))
            .wrapContentSize(Alignment.Center)
    ) {
        Header()
        //AddWallpaperButton()
        SearchTextField()
        WallpapersLazyGrid()
    }

}

@Composable
fun AddWallpaperButton() {
    FloatingActionButton(
        onClick = { /*TODO*/ },
        backgroundColor = colorResource(id = R.color.main_theme),
        contentColor = Color.White
    ) {
        Icon(Icons.Filled.Add, contentDescription = "add")
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen()
}

@Composable
fun Header() {

    Row(
        modifier = Modifier.padding(start = 16.dp, top = 48.dp),
        verticalAlignment = Alignment.Bottom
    ) {
        Text(
            text = stringResource(id = R.string.wallpaper),
            fontSize = 24.sp,
            fontFamily = FontFamily(Font(R.font.raleway_bold))
        )
        Image(
            modifier = Modifier.size(width = 24.dp, height = 24.dp),
            painter = painterResource(id = R.drawable.wallpaper_icon),
            contentDescription = "place icon",
            contentScale = ContentScale.FillWidth,
        )
    }

}

@Composable
fun SearchTextField() {

    var textState by remember { mutableStateOf("") }

    OutlinedTextField(
        value = textState,
        onValueChange = { textState = it },
        shape = RoundedCornerShape(12.dp),
        placeholder = { Text(text = "search") },
        trailingIcon = {
            Image(
                painter = painterResource(id = R.drawable.search_icon),
                contentDescription = "search"
            )
        },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = colorResource(id = R.color.main_theme),
            disabledBorderColor = colorResource(id = R.color.main_theme),
        ),
        singleLine = true,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, top = 18.dp, bottom = 11.dp)
            .border(
                width = 2.dp,
                color = colorResource(id = R.color.main_theme),
                shape = RoundedCornerShape(12.dp)
            )
    )

}

@Composable
fun WallpapersLazyGrid() {

    val wallpapers = getWallpapers()

    LazyVerticalGrid(
        modifier = Modifier.padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp),
        horizontalArrangement = Arrangement.spacedBy(14.dp),
        columns = GridCells.Adaptive(minSize = 157.dp)
    ) {
        items(items = wallpapers) { wallpaper ->
            WallpaperItem(wallpaper)
        }
    }

}

@Preview(showBackground = true)
@Composable
fun WallpaperItem(@PreviewParameter(SampleWallpaperProvider::class) wallpaper: Wallpaper) {

    Card(
        modifier = Modifier
            .width(157.dp)
            .height(279.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = 7.dp,

        ) {
        // AsyncImage
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(id = R.drawable.wallpaper_icon),
            contentDescription = "image",
            contentScale = ContentScale.FillWidth,
        )
        Column(
            modifier = Modifier.padding(top = 8.dp, end = 8.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.End
        ) {
            Image(
                modifier = Modifier
                    .size(16.dp, 16.dp),
                painter = painterResource(
                    id = if (wallpaper.isFavourite) R.drawable.heart_checked else R.drawable.heart_unchecked
                ),
                contentDescription = "fav",
                contentScale = ContentScale.Inside,
            )
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = wallpaper.name,
                fontSize = 16.sp,
                fontFamily = FontFamily(Font(R.font.raleway_bold)),
                textAlign = TextAlign.Center
            )
        }
    }

}

class SampleWallpaperProvider : PreviewParameterProvider<Wallpaper> {
    override val values = sequenceOf(
        Wallpaper(name = "Toroni", imgUrl = "")
    )
}