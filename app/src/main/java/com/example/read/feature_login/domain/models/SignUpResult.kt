package com.example.read.feature_login.domain.models

import kotlinx.datetime.Instant

data class SignUpResult(
    val id: String? = null,
    val email: String? = null,
    val createdAt: Instant? = null,
    val updatedAt: Instant? = null,
    val confirmationSentAt: Instant? = null
)