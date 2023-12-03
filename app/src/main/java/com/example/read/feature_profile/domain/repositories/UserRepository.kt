package com.example.read.feature_profile.domain.repositories

import com.example.read.feature_profile.domain.models.User
import com.example.read.feature_profile.domain.models.UserSession
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    suspend fun getUser(): User?

    suspend fun getRefreshedUser(refreshToken: String): UserSession

    val userSessionFlow: Flow<UserSession>

    val guestUsernameFlow: Flow<String>

    suspend fun updateUserSession(userSession: UserSession)

    suspend fun updateGuestUsername(username: String)

    suspend fun logout()
}