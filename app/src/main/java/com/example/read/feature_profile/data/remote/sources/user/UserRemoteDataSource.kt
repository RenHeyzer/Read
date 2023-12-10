package com.example.read.feature_profile.data.remote.sources.user

import io.github.jan.supabase.gotrue.SessionStatus
import io.github.jan.supabase.gotrue.user.UserInfo
import io.github.jan.supabase.gotrue.user.UserSession

interface UserRemoteDataSource {

    suspend fun getUser(): UserInfo?

    suspend fun getSessionStatus(
        authenticated: (suspend (session: UserSession) -> Unit)? = null,
        notAuthenticated: (suspend () -> Unit)? = null,
        loadingFromStorage: (suspend () -> Unit)? = null,
        networkError: (suspend () -> Unit)? = null,
    )

    val sessionStatus: SessionStatus

    suspend fun logout()
}