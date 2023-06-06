package com.example.wallpaperapp.screens.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wallpaperapp.tools.DataHandler
import com.example.wallpaperapp.user.User
import com.example.wallpaperapp.user.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AuthViewModel(private val userRepo: UserRepository) : ViewModel() {

    private val _uiState: MutableStateFlow<DataHandler<User>> = MutableStateFlow(
        DataHandler.IDLE())
    val uiState: StateFlow<DataHandler<User>> = _uiState

    fun signIn(email: String, password: String) {
        viewModelScope.launch {
            _uiState.update { DataHandler.LOADING() }
            _uiState.update { userRepo.setAppUser(email, password) }
        }
    }

    fun signUp(email: String, password: String) {
        viewModelScope.launch {
            _uiState.update { DataHandler.LOADING() }
            _uiState.update { userRepo.newAppUser(email, password) }
        }
    }

}