package com.example.wallpaperapp.user

class DefaultAppUserStorage() {

    fun getUser(): User {
        return User(
            name = "unauthorized",
            isAuth = false
        )
    }

}