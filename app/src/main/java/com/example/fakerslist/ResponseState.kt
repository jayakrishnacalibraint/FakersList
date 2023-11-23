package com.example.fakerslist

sealed class ResponseState<out T : Any> {
    data class Success<out T : Any>(val data: T) : ResponseState<T>()
    data class Error(val exception: Exception) : ResponseState<Nothing>()
    data object Loading : ResponseState<Nothing>()
}
