package com.example.wallpaperapp.tools

inline fun <T> safeCall(action: () -> DataHandler<T>): DataHandler<T> {
    return try {
        action()
    } catch (e: Exception) {
        DataHandler.ERROR(null, e.message ?: "An unknown Error Occurred")
    }
}