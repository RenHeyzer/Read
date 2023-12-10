package com.example.read.feature_profile.domain.repositories

import com.example.read.feature_profile.domain.models.Session
import com.example.read.feature_profile.domain.models.User
import kotlinx.coroutines.flow.Flow

interface ProfileRepository {

    suspend fun getUser(): User?


    val guestUsernameFlow: Flow<String>

    suspend fun updateGuestUsername(username: String)

    suspend fun getSessionStatus(
        authenticated: (suspend (session: Session) -> Unit)? = null,
        notAuthenticated: (suspend () -> Unit)? = null,
        loadingFromStorage: (suspend () -> Unit)? = null,
        networkError: (suspend () -> Unit)? = null,
    )

    val sessionStatus: SessionStatusState

    suspend fun logout()
}

sealed interface SessionStatusState {

    data object NotAuthenticated : SessionStatusState

    data object LoadingFromStorage : SessionStatusState

    data object NetworkError : SessionStatusState

    @JvmInline
    value class Authenticated(val session: Session) : SessionStatusState
}