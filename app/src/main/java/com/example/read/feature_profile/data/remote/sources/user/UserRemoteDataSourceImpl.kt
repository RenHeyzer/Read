package com.example.read.feature_profile.data.remote.sources.user

import io.github.jan.supabase.gotrue.GoTrue
import io.github.jan.supabase.gotrue.SessionStatus
import io.github.jan.supabase.gotrue.user.UserInfo
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class UserRemoteDataSourceImpl @Inject constructor(
    private val gotrue: GoTrue,
) : UserRemoteDataSource {

    override suspend fun getUser(): UserInfo? {
        return gotrue.currentUserOrNull()
    }

    override val sessionStatus: StateFlow<SessionStatus>
        get() = gotrue.sessionStatus

    override suspend fun logout() {
        gotrue.logout()
    }
}