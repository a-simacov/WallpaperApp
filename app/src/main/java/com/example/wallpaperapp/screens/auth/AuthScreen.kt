package com.example.wallpaperapp.screens.auth

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.wallpaperapp.R
import com.example.wallpaperapp.navigation.NavigationItem
import com.example.wallpaperapp.screens.common.AppViewModelProvider
import com.example.wallpaperapp.screens.common.ShowProgress
import com.example.wallpaperapp.tools.DataHandler

@Composable
fun AuthScreen(
    navController: NavHostController,
    isNewUser: Boolean,
    vm: AuthViewModel = viewModel(factory = AppViewModelProvider.provide())
) {
    var emailState by remember { mutableStateOf("") }
    var passwordState by remember { mutableStateOf("") }

    val onContinueClick = {
        if (isNewUser)
            vm.signUp(emailState, passwordState)
        else
            vm.signIn(emailState, passwordState)
    }
    val onEmailChange: (String) -> Unit = { emailState = it }
    val onPasswordChange: (String) -> Unit = { passwordState = it }

    val uiState by vm.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(id = R.color.main_theme)),
        verticalArrangement = Arrangement.Center
    ) {
        when (uiState) {
            is DataHandler.IDLE -> SignInCard(
                email = emailState,
                password = passwordState,
                isNewUser = isNewUser,
                onContinueClick = onContinueClick,
                onEmailChange = onEmailChange,
                onPasswordChange = onPasswordChange
            )
            is DataHandler.LOADING -> ShowProgress()
            else -> {
                uiState.message?.also { message ->
                    Toast.makeText(LocalContext.current, message, Toast.LENGTH_LONG).show()
                }
                navController.popBackStack(NavigationItem.Home.route, inclusive = false)
            }
        }
    }
}

@Composable
fun SignInCard(
    email: String,
    password: String,
    isNewUser: Boolean,
    onContinueClick: () -> Unit,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit
) {

//    Column(
//        modifier = Modifier.fillMaxSize()
//            .background(color = colorResource(id = R.color.main_theme)),
//        verticalArrangement = Arrangement.Center
//    ) {
        Card(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 25.dp),
            shape = RoundedCornerShape(10.dp)
        ) {
            Column(
                modifier = Modifier.padding(horizontal = 30.dp, vertical = 40.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Sign in",
                    modifier = Modifier.padding(bottom = 30.dp)
                )
                Text(text = "Email")
                OutlinedTextField(
                    value = email,
                    onValueChange = onEmailChange,
                    singleLine = true
                )
                Text(text = "Password")
                OutlinedTextField(
                    value = password,
                    onValueChange = onPasswordChange,
                    singleLine = true
                )
                if (isNewUser) {
                    Text(text = "Confirm password")
                    OutlinedTextField(
                        value = password,
                        onValueChange = onPasswordChange,
                        singleLine = true
                    )
                }
                Button(
                    onClick = onContinueClick,
                    enabled = email.isNotEmpty() && password.isNotEmpty(),
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
//    }

}

@Preview(showBackground = true)
@Composable
fun SignInPreview() {
    SignInCard(
        email = "email@aa.com",
        password = "132246",
        isNewUser = true,
        onContinueClick = {},
        onEmailChange = {},
        onPasswordChange = {},
    )
}