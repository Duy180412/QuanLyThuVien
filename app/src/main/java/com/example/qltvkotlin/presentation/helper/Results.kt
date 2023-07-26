package com.example.qltvkotlin.presentation.helper

sealed class Results<T> {
    data class Loading<T>(val message: String? = null) : Results<T>()
    data class Success<T>(val message: String, val value: T?) : Results<T>()
    data class Error<T>(val message: String?= null) : Results<T>()
}