package com.example.read.feature_auth.data.repositories

import com.example.read.common.data.base.BaseRepository
import com.example.read.common.utils.AppDispatchers
import com.example.read.feature_auth.data.remote.sources.AuthRemoteDataSource
import com.example.read.feature_auth.domain.repositories.AuthRepository
import javax.inject.Inject

private const val MIN_SDK = "28"

class AuthRepositoryImpl @Inject constructor(
    private val appDispatchers: AppDispatchers,
    private val authRemoteDataSource: AuthRemoteDataSource,
) : BaseRepository(appDispatchers), AuthRepository {

    override suspend fun signUp(email: String, password: String): Boolean {
        val result = authRemoteDataSource.signUpWithEmailAndPassword(email, password)
        return if (result != null) {
            result.user?.sendEmailVerification()
            true
        } else {
            false
        }
    }

    override suspend fun signIn(email: String, password: String): Boolean {
        val result = authRemoteDataSource.signInWithEmailAndPassword(email, password)
        return result != null
    }

    override val isEmailVerified: Boolean
        get() = authRemoteDataSource.isEmailVerified

    /*private suspend fun sendConfirmationEmail(user: FirebaseUser) {
        val actionCodeSettings = actionCodeSettings {
            url = "https://www.read.com"
            handleCodeInApp = true
            setAndroidPackageName(
                BuildConfig.APPLICATION_ID,
                true,
                MIN_SDK
            )
        }
        user.sendEmailVerification(actionCodeSettings).await()
    }*/
}