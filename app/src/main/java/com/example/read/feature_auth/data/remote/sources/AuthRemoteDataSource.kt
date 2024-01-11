package com.example.read.feature_auth.data.remote.sources

import com.google.firebase.auth.AuthResult

interface AuthRemoteDataSource {

    suspend fun signUpWithEmailAndPassword(email: String, password: String): AuthResult?

    suspend fun signInWithEmailAndPassword(email: String, password: String): AuthResult?

    val isEmailVerified: Boolean
}