package com.example.read.utils.mappers

import com.example.read.feature_profile.domain.models.User
import com.google.gson.Gson
import io.github.jan.supabase.gotrue.user.UserInfo
import io.github.jan.supabase.gotrue.user.UserSession
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject

fun UserSession.asDomain() = com.example.read.feature_profile.domain.models.UserSession(
    accessToken = accessToken,
    refreshToken = refreshToken,
    providerRefreshToken = providerRefreshToken,
    providerToken = providerToken,
    expiresIn = expiresIn,
    expiresAt = expiresAt,
    tokenType = tokenType,
    user = user?.asDomain(),
    type = type
)

fun UserInfo.asDomain() = User(
    id = id,
    email = email,
    createdAt = createdAt,
    confirmedAt = confirmedAt,
    confirmationSentAt = confirmationSentAt,
    emailConfirmedAt = emailConfirmedAt,
    lastSignInAt = lastSignInAt,
    updatedAt = updatedAt,
    role = role,
    emailChangeSentAt = emailChangeSentAt,
    userMetadata = Gson().fromJson(userMetadata.toString(), User.UserMetadata::class.java)
)

fun com.example.read.feature_profile.domain.models.UserSession.asData() = UserSession(
    accessToken = accessToken,
    refreshToken = refreshToken,
    providerRefreshToken = providerRefreshToken,
    providerToken = providerToken,
    expiresIn = expiresIn,
    expiresAt = expiresAt,
    tokenType = tokenType,
    user = user?.asData(),
    type = type
)

fun User.asData() = UserInfo(
    id = id,
    email = email,
    createdAt = createdAt,
    confirmedAt = confirmedAt,
    confirmationSentAt = confirmationSentAt,
    emailConfirmedAt = emailConfirmedAt,
    lastSignInAt = lastSignInAt,
    updatedAt = updatedAt,
    role = role,
    emailChangeSentAt = emailChangeSentAt,
    aud = ""
)