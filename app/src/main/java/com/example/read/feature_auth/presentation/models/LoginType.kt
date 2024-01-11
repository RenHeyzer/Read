package com.example.read.feature_auth.presentation.models

sealed interface LoginType {

    data class SignUp(
        val username: String,
        val email: String,
        val password: String,
        val confirmPassword: String
    ) : LoginType

    data class SignIn(
        val email: String,
        val password: String,
    ) : LoginType

    data object Google : LoginType
}