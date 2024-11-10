package com.example.hwaa.domain

sealed class Response<out T> {
    data class Success<out T>(val data: T) : Response<T>()
    data class Error(val exception: String) : Response<Nothing>()
}