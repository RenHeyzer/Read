package com.example.read.feature_auth.presentation.models

data class EmptyProperties(
    val username: Boolean = false,
    val email: Boolean = false,
    val password: Boolean = false,
    val confirmPassword: Boolean = false,
    val notEqualsPasswords: Boolean = false
)