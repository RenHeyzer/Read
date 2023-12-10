package com.example.read.feature_profile.domain.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

data class User(
    val id: String,
    val email: String? = null,
    val metadata: UserMetadata
)

@Serializable
data class UserMetadata(
    @SerialName("username")
    val username: String,
    @SerialName("profile_image")
    val profileImage: String
)