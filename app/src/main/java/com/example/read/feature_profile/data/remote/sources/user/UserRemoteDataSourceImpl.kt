package com.example.read.feature_profile.data.remote.sources.user

import io.github.jan.supabase.gotrue.GoTrue
import io.github.jan.supabase.gotrue.SessionStatus
import io.github.jan.supabase.gotrue.user.UserInfo
import io.github.jan.supabase.gotrue.user.UserSession
import javax.inject.Inject

class UserRemoteDataSourceImpl @Inject constructor(
    private val gotrue: GoTrue,
) : UserRemoteDataSource {

    override suspend fun getUser(): UserInfo? {
        return gotrue.currentUserOrNull()
    }

    override suspend fun getSessionStatus(
        authenticated: (suspend (session: UserSession) -> Unit)?,
        notAuthenticated: (suspend () -> Unit)?,
        loadingFromStorage: (suspend () -> Unit)?,
        networkError: (suspend () -> Unit)?
    ) {
        gotrue.sessionStatus.collect { status ->
            when (status) {
                is SessionStatus.Authenticated -> authenticated?.let {
                    it(status.session)
                }
                SessionStatus.NotAuthenticated -> notAuthenticated?.let {
                    it()
                }
                SessionStatus.LoadingFromStorage -> loadingFromStorage?.let {
                    it()
                }
                SessionStatus.NetworkError -> networkError?.let {
                    it()
                }
            }
        }
    }

    override val sessionStatus: SessionStatus
        get() = gotrue.sessionStatus.value

    override suspend fun logout() {
        gotrue.logout()
    }
}