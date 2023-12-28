package com.example.read.common.presentation.utils

sealed class UiState<T> {

    class Loading<T> : UiState<T>()

    class Error<T>(val message: String? = null) : UiState<T>()

    class Success<T>(val data: T? = null) : UiState<T>()
}