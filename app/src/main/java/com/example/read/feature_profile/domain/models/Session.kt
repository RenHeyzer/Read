package com.example.read.feature_profile.domain.models

import io.github.jan.supabase.gotrue.user.UserInfo

data class Session(
    val accessToken: String,
    val refreshToken: String,
    val expiresIn: Long,
    val user: UserInfo?
)