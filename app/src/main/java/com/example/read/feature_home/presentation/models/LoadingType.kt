package com.example.read.feature_home.presentation.models

sealed interface LoadingType {

    class Books(val value: Boolean) : LoadingType

    class Recommendations(val value: Boolean) : LoadingType

    class Slides(val value: Boolean) : LoadingType
}