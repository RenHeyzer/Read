package com.example.read.feature_profile.data.remote.sources.user

import io.github.jan.supabase.gotrue.GoTrue
import io.github.jan.supabase.gotrue.user.UserInfo
import io.github.jan.supabase.gotrue.user.UserSession
import javax.inject.Inject

class UserRemoteDataSourceImpl @Inject constructor(
    private val gotrue: GoTrue,
) : UserRemoteDataSource {

    override suspend fun getUser(): UserInfo? {
        return gotrue.currentUserOrNull()
    }

    override suspend fun getRefreshedSession(refreshToken: String) =
        gotrue.refreshSession(refreshToken)

    override suspend fun logout() {
        gotrue.logout()
    }
}