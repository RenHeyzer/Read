package com.example.read.feature_profile.data.repositories

import com.example.read.feature_profile.data.local.preferences.GuestManager
import com.example.read.feature_profile.data.remote.sources.user.UserRemoteDataSource
import com.example.read.feature_profile.domain.models.UserSession
import com.example.read.feature_profile.domain.repositories.UserRepository
import com.example.read.main.data.local.preferences.UserSessionManager
import com.example.read.utils.base.BaseRepository
import com.example.read.utils.dispatchers.AppDispatchers
import com.example.read.utils.mappers.asData
import com.example.read.utils.mappers.asDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val appDispatchers: AppDispatchers,
    private val userRemoteDataSource: UserRemoteDataSource,
    private val guestManager: GuestManager,
    private val userSessionManager: UserSessionManager
) : BaseRepository(appDispatchers), UserRepository {

    override suspend fun getUser() = withContext(appDispatchers.io) {
        userRemoteDataSource.getUser()?.asDomain()
    }

    override suspend fun getRefreshedSession(refreshToken: String) =
        userRemoteDataSource.getRefreshedSession(refreshToken).asDomain()

    override val userSessionFlow: Flow<UserSession>
        get() = userSessionManager.userSessionFlow.map {
            it.asDomain()
        }


    override val guestUsernameFlow: Flow<String>
        get() = guestManager.guestUsernameFlow

    override suspend fun updateUserSession(userSession: UserSession) {
        userSessionManager.updateUserSessionPreferences(userSession.asData())
    }

    override suspend fun updateGuestUsername(username: String) {
        guestManager.updateQuestUsername(username)
    }

    override suspend fun logout() {
        withContext(appDispatchers.io) {
            userRemoteDataSource.logout()
        }
    }
}