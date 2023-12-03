package com.example.read.feature_login.domain.repositories

import com.example.read.feature_login.domain.models.SignUpParams
import com.example.read.feature_login.domain.models.SignUpResult
import kotlinx.coroutines.flow.Flow

interface LoginRepository {

    suspend fun signUp(params: SignUpParams): SignUpResult

    suspend fun login(email: String, password: String)

    suspend fun loginWithGoogle()

    suspend fun sentOtpToEmail(email: String)

    suspend fun resendEmailConfirmation(email: String)
}