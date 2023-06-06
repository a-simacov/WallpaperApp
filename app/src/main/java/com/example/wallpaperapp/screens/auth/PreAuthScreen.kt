package com.example.wallpaperapp.screens.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.wallpaperapp.R
import com.example.wallpaperapp.navigation.NavigationItem

@Composable
fun PreAuthScreen(navController: NavHostController) {

    val onSignUpClick = {
        navController.navigate(NavigationItem.SignUp.route)
    }
    val onSignInClick = {
        navController.navigate(NavigationItem.SignIn.route)
    }
    val onCancelClick: () -> Unit = {
        navController.popBackStack(NavigationItem.Home.route, inclusive = false)
    }

    Column(
        modifier = Modifier.fillMaxSize()
            .background(color = colorResource(id = R.color.main_theme)),
        verticalArrangement = Arrangement.Center
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 55.dp),
            shape = RoundedCornerShape(10.dp),
            backgroundColor = colorResource(id = R.color.main_theme)
        ) {
            Column(
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Please register\nto add\nwallpapers",
                    fontFamily = FontFamily(Font(R.font.raleway_regular)),
                    fontSize = 30.sp,
                    color = colorResource(id = R.color.white),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(10.dp))
                PreAuthButton(caption = "Sign in", onClick = onSignInClick)
                PreAuthButton(caption = "Sign up", onClick = onSignUpClick)
                PreAuthButton(caption = "Cancel", onClick = onCancelClick)
            }
        }
    }

}

@Composable
fun PreAuthButton(caption: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = colorResource(id = R.color.white),
            contentColor = colorResource(id = R.color.black),
        ),
        shape = RoundedCornerShape(4.dp)
    ) {
        Text(
            text = caption,
            fontFamily = FontFamily(Font(R.font.raleway_regular)),
            fontSize = 24.sp,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreAuthScreenPreview() {
    PreAuthScreen(navController = rememberNavController())
}