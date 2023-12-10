package com.example.read.utils.mappers

import com.example.read.feature_profile.domain.models.Session
import io.github.jan.supabase.gotrue.user.UserSession

fun UserSession.asDomain() = Session(
    accessToken = accessToken,
    refreshToken = refreshToken,
    expiresIn = expiresIn,
    user = user
)