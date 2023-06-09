package com.example.wallpaperapp.screens.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
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
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.wallpaperapp.R
import com.example.wallpaperapp.data.Wallpaper
import com.example.wallpaperapp.navigation.NavigationItem
import com.example.wallpaperapp.tools.DataHandler

@Composable
fun WallpapersCommonScreen(
    navController: NavHostController,
    headerText: String,
    showAddButton: Boolean = false,
    vm: WallpapersScreenVM
) {

    val onClickAddBtn = { navController.navigate(NavigationItem.NewWallpaper.route) }
    val onClickWallPaper: (wallpaper: Wallpaper) -> Unit = {
        navController.navigate(route = NavigationItem.SingleWallpaper.passUrl(it))
    }
    val onSetFav: (wallpaper: Wallpaper) -> Unit = {
        it.isFavourite.value = !it.isFavourite.value
        vm.changeFav(it)
    }

    val dataHandler by vm.wallpapersDataHandler.collectAsState()

    LaunchedEffect(Unit) {
        vm.updateWallpapers(navController.currentDestination!!.route!!.uppercase())
    }

    Scaffold(
        floatingActionButton = {
            if (showAddButton)
                AddWallpaperButton(onClickAddBtn = { onClickAddBtn() })
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(colorResource(id = R.color.white))
        ) {
            Header(headerText)
            SearchTextField()
            when (dataHandler) {
                is DataHandler.LOADING -> ShowProgress(progressColor = colorResource(id = R.color.main_theme))
                else -> dataHandler.data?.let { WallpapersLazyGrid(it, onClickWallPaper, onSetFav) }
            }
        }
    }

}

@Composable
fun AddWallpaperButton(onClickAddBtn: () -> Unit, modifier: Modifier = Modifier) {
    FloatingActionButton(
        modifier = modifier,
        onClick = onClickAddBtn,
        backgroundColor = colorResource(id = R.color.main_theme),
        contentColor = Color.White
    ) {
        Icon(Icons.Filled.Add, contentDescription = "add")
    }
}

@Composable
fun Header(text: String) {

    Row(
        modifier = Modifier.padding(start = 16.dp, top = 48.dp),
        verticalAlignment = Alignment.Bottom
    ) {
        Text(
            text = text,
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
fun WallpapersLazyGrid(
    wallpapers: List<Wallpaper>,
    onClickWallpaper: (Wallpaper) -> Unit,
    onSetFav: (Wallpaper) -> Unit
) {

    LazyVerticalGrid(
        modifier = Modifier.padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp),
        horizontalArrangement = Arrangement.spacedBy(14.dp),
        columns = GridCells.Adaptive(minSize = 157.dp)
    ) {
        items(
            items = wallpapers,
            key = { item -> item.id }
        ) { wallpaper ->
            WallpaperItem(wallpaper, onClickWallpaper, onSetFav)
        }
    }

}

//@Preview(showBackground = true)
@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun WallpaperItem(
    @PreviewParameter(SampleWallpaperProvider::class) wallpaper: Wallpaper,
    onClickWallpaper: (Wallpaper) -> Unit,
    onSetFav: (Wallpaper) -> Unit
) {

    Card(
        modifier = Modifier
            .size(width = 157.dp, height = 279.dp)
            .clickable { onClickWallpaper(wallpaper) },
        shape = RoundedCornerShape(12.dp),
        elevation = 7.dp,
    ) {
        GlideImage(
            modifier = Modifier.fillMaxSize(),
            model = wallpaper.imgUrl,
            contentDescription = "image",
            contentScale = ContentScale.FillWidth,
        ) {
            it
                .error(R.drawable.ic_launcher_background)
                .placeholder(R.drawable.wallpaper_icon)
                .load(wallpaper.imgUrl)
        }
        Column(
            modifier = Modifier.padding(top = 8.dp, end = 8.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.End,
        ) {
            Image(
                modifier = Modifier
                    .size(64.dp)
                    .clickable {
                        onSetFav(wallpaper)
                    },
                painter = painterResource(
                    id = if (wallpaper.isFavourite.value) R.drawable.heart_checked else R.drawable.heart_unchecked
                ),
                contentDescription = "fav",
                contentScale = ContentScale.Fit,
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
        Wallpaper(name = "Toroni", imgUrl = "", id = "")
    )
}