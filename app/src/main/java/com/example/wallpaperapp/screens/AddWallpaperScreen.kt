package com.example.wallpaperapp.screens

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.wallpaperapp.R
import com.example.wallpaperapp.navigation.NavigationItem

@Composable
fun AddWallpaperScreen(
    navController: NavHostController,
    vm: AddWallpaperScreenViewModel = viewModel()
) {
    var imgNameState by remember { mutableStateOf("") }
    var imgUriState by remember { mutableStateOf<Uri?>(null) }
    var inProcess by remember { mutableStateOf(false) }
    val selectImageLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.GetContent()
    ) {
        imgUriState = it
    }

    val closeScreen by vm.opExecuted.collectAsState()
    if (closeScreen) navController.popBackStack(NavigationItem.Home.route, inclusive = false)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(id = R.color.main_theme)),
        verticalArrangement = Arrangement.Center
    ) {
        if (inProcess)
            ShowProgress()
        else
            AddWallpaper(
                imgUriState = imgUriState,
                imgNameState = imgNameState,
                onImgNameChange = { imgNameState = it },
                onImgChoose = { selectImageLauncher.launch("image/*") },
                onContinueBtnClick = {
                    inProcess = true
//                    val newWallpaper = Wallpaper(name = imgNameState, imgLocalUri = imgUriState!!)
//                    vm.saveImage(newWallpaper)
                }
            )
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun AddWallpaper(
    imgUriState: Uri?,
    imgNameState: String,
    onImgNameChange: (String) -> Unit,
    onImgChoose: () -> Unit,
    onContinueBtnClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 25.dp),
        shape = RoundedCornerShape(10.dp)
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 30.dp, vertical = 40.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Download",
                modifier = Modifier.padding(bottom = 30.dp)
            )
            Text(
                text = "Name"
            )
            OutlinedTextField(
                value = imgNameState,
                onValueChange = onImgNameChange,
                singleLine = true
            )
            Button(
                onClick = onImgChoose,
                modifier = Modifier
                    .width(150.dp)
                    .padding(vertical = 20.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = colorResource(id = R.color.main_theme),
                    contentColor = colorResource(id = R.color.white),
                ),
            ) {
                Text(
                    text = "Choose file",
                    fontFamily = FontFamily(Font(R.font.raleway_regular)),
                    fontSize = 16.sp,
                )
            }
            if (imgUriState != null) {
                GlideImage(
                    model = imgUriState,
                    contentDescription = "chosen image",
                    modifier = Modifier.size(width = 300.dp, height = 300.dp),
                    contentScale = ContentScale.Fit
                )
            }
            Button(
                onClick = onContinueBtnClick,
                enabled = imgUriState != null,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = colorResource(id = R.color.black),
                    contentColor = colorResource(id = R.color.white),
                ),
                shape = RoundedCornerShape(4.dp)
            ) {
                Text(
                    text = "Continue",
                    fontFamily = FontFamily(Font(R.font.raleway_regular)),
                    fontSize = 16.sp,
                )
            }
        }
    }

}