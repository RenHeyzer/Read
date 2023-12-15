package com.example.read.feature_profile.data.remote.sources.user

import io.github.jan.supabase.gotrue.SessionStatus
import io.github.jan.supabase.gotrue.user.UserInfo
import io.github.jan.supabase.gotrue.user.UserSession
import kotlinx.coroutines.flow.StateFlow

interface UserRemoteDataSource {

    suspend fun getUser(): UserInfo?

    val sessionStatus: StateFlow<SessionStatus>

    suspend fun logout()
}