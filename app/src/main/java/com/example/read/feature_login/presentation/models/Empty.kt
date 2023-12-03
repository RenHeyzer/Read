package com.example.read.feature_login.presentation.models

data class Empty(
    val username: Boolean = false,
    val email: Boolean = false,
    val password: Boolean = false,
    val confirmPassword: Boolean = false,
    val notEqualsPasswords: Boolean = false
)