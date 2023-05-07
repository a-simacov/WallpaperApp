package com.example.wallpaperapp.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wallpaperapp.R

@Preview
@Composable
fun AddWallpaperScreen() {
    var textState by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize()
            .background(color = colorResource(id = R.color.main_theme)),
        verticalArrangement = Arrangement.Center
    ) {
        Card(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 25.dp),
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
                    value = textState,
                    onValueChange = { textState = it },
                )
                Button(
                    onClick = { /*TODO*/ },
                    modifier = Modifier.width(150.dp).padding(vertical = 20.dp),
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
                Button(
                    onClick = { /*TODO*/ },
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
}