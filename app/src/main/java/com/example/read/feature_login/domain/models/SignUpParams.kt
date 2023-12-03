package com.example.read.feature_login.domain.models

data class SignUpParams(
    val username: String,
    val profileImage: String = "",
    val email: String,
    val password: String
)