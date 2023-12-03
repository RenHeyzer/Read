package com.example.read.feature_login.data.repositories

import com.example.read.main.data.local.preferences.UserSessionManager
import com.example.read.feature_login.data.remote.sources.LoginRemoteDataSource
import com.example.read.feature_login.domain.models.SignUpParams
import com.example.read.feature_login.domain.models.SignUpResult
import com.example.read.feature_login.domain.repositories.LoginRepository
import com.example.read.utils.base.BaseRepository
import com.example.read.utils.dispatchers.AppDispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val appDispatchers: AppDispatchers,
    private val signRemoteDataSource: LoginRemoteDataSource,
) : BaseRepository(appDispatchers), LoginRepository {

    override suspend fun signUp(params: SignUpParams): SignUpResult {
        withContext(appDispatchers.io) {
            signRemoteDataSource.signUp(params)
        }.apply {
            return SignUpResult(
                id = id,
                email = this.email,
                createdAt = createdAt,
                updatedAt = updatedAt,
                confirmationSentAt = confirmationSentAt,
            )
        }
    }

    override suspend fun login(email: String, password: String) {
        withContext(appDispatchers.io) {
            signRemoteDataSource.login(email, password)
        }
    }

    override suspend fun loginWithGoogle() {
        withContext(appDispatchers.io) {
            signRemoteDataSource.loginWithGoogle()
        }
    }

    override suspend fun sentOtpToEmail(email: String) {
        withContext(appDispatchers.io) {
            signRemoteDataSource.sendOtpToEmail(email)
        }
    }

    override suspend fun resendEmailConfirmation(email: String) {
        withContext(appDispatchers.io) {
            signRemoteDataSource.resendEmailConfirmation(email)
        }
    }
}