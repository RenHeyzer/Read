package com.example.read.feature_profile.domain.models

data class Session(
    val accessToken: String,
    val refreshToken: String,
    val expiresIn: Long,
    val user: User?
)