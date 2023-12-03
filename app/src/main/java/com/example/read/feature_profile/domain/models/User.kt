package com.example.read.feature_profile.domain.models

import com.example.read.utils.mappers.Mappable
import io.github.jan.supabase.gotrue.user.UserInfo
import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

data class User(
    val id: String,
    val email: String? = null,
    val createdAt: Instant? = null,
    val confirmedAt: Instant? = null,
    val confirmationSentAt: Instant? = null,
    val emailConfirmedAt: Instant? = null,
    val lastSignInAt: Instant? = null,
    val updatedAt: Instant? = null,
    val role: String? = null,
    val emailChangeSentAt: Instant? = null,
    val userMetadata: UserMetadata
) : Mappable {
    data class UserMetadata(
        val username: String,
        val profileImage: String
    )
}
