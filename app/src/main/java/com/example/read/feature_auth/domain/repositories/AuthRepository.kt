package com.example.read.feature_auth.domain.repositories

interface AuthRepository {

    suspend fun signUp(email: String, password: String): Boolean

    suspend fun signIn(email: String, password: String): Boolean

    val isEmailVerified: Boolean
}