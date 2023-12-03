package com.example.read.feature_login.data.remote.sources

import com.example.read.feature_login.domain.models.SignUpParams
import io.github.jan.supabase.gotrue.providers.builtin.Email

interface LoginRemoteDataSource {

    suspend fun signUp(params: SignUpParams): Email.Result

    suspend fun login(email: String, password: String)

    suspend fun loginWithGoogle()

    suspend fun sendOtpToEmail(email: String)

    suspend fun resendEmailConfirmation(email: String)
}