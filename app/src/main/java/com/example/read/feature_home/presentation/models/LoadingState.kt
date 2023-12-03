package com.example.read.feature_home.presentation.models

data class LoadingState(
    val booksLoading: Boolean = true,
    val recommendationsLoading: Boolean = true,
    val slidesLoading: Boolean = true
)