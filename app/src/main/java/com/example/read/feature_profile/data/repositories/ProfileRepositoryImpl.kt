package com.example.read.feature_profile.data.repositories

import com.example.read.feature_profile.data.local.preferences.GuestManager
import com.example.read.feature_profile.data.remote.sources.user.UserRemoteDataSource
import com.example.read.feature_profile.domain.models.Session
import com.example.read.feature_profile.domain.repositories.ProfileRepository
import com.example.read.feature_profile.domain.repositories.SessionStatusState
import com.example.read.utils.base.BaseRepository
import com.example.read.utils.dispatchers.AppDispatchers
import com.example.read.utils.mappers.asDomain
import io.github.jan.supabase.gotrue.SessionStatus
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
    private val appDispatchers: AppDispatchers,
    private val userRemoteDataSource: UserRemoteDataSource,
    private val guestManager: GuestManager,
) : BaseRepository(appDispatchers), ProfileRepository {

    override suspend fun getUser() = withContext(appDispatchers.io) {
        userRemoteDataSource.getUser()?.asDomain()
    }

    override val guestUsernameFlow: Flow<String>
        get() = guestManager.guestUsernameFlow

    override suspend fun updateGuestUsername(username: String) {
        guestManager.updateQuestUsername(username)
    }

    override suspend fun getSessionStatus(
        authenticated: (suspend (session: Session) -> Unit)?,
        notAuthenticated: (suspend () -> Unit)?,
        loadingFromStorage: (suspend () -> Unit)?,
        networkError: (suspend () -> Unit)?
    ) {
        userRemoteDataSource.sessionStatus.collect { status ->
            when (status) {
                is SessionStatus.Authenticated -> authenticated?.let {
                    it(status.session.asDomain())
                }
                SessionStatus.LoadingFromStorage -> loadingFromStorage?.let {
                    it()
                }
                SessionStatus.NetworkError -> networkError?.let {
                    it()
                }
                SessionStatus.NotAuthenticated -> notAuthenticated?.let {
                    it()
                }
            }
        }
    }

    override val sessionStatus: SessionStatusState
        get() = when (val status = userRemoteDataSource.sessionStatus.value) {
            is SessionStatus.Authenticated -> SessionStatusState.Authenticated(status.session.asDomain())
            SessionStatus.LoadingFromStorage -> SessionStatusState.LoadingFromStorage
            SessionStatus.NetworkError -> SessionStatusState.NetworkError
            SessionStatus.NotAuthenticated -> SessionStatusState.NotAuthenticated
        }

    override suspend fun logout() {
        withContext(appDispatchers.io) {
            userRemoteDataSource.logout()
        }
    }
}