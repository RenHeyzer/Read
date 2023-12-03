package com.example.read.feature_profile.domain.models

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlin.time.Duration.Companion.seconds

data class UserSession(
    val accessToken: String,
    val refreshToken: String,
    val providerRefreshToken: String? = null,
    val providerToken: String? = null,
    val expiresIn: Long,
    val tokenType: String,
    val user: User?,
    val type: String = "",
    val expiresAt: Instant = Clock.System.now() + (expiresIn.seconds),
)