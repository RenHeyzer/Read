package com.example.read.feature_profile.data.remote.sources.user

import io.github.jan.supabase.gotrue.user.UserInfo
import io.github.jan.supabase.gotrue.user.UserSession

interface UserRemoteDataSource {

    suspend fun getUser(): UserInfo?

    suspend fun getRefreshedSession(refreshToken: String): UserSession

    suspend fun logout()
}