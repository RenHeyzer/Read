package com.example.read.main.data.local.preferences

import android.content.Context
import androidx.datastore.dataStore
import com.example.read.utils.constants.Constants
import com.example.read.utils.serializers.UserSessionSerializer
import dagger.hilt.android.qualifiers.ApplicationContext
import io.github.jan.supabase.gotrue.user.UserSession
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore by dataStore(Constants.USER_PREFERENCES_NAME, UserSessionSerializer)

@Singleton
class UserSessionManager @Inject constructor(
    @ApplicationContext context: Context
) {

    private val userDataStore = context.dataStore

    val userSessionFlow: Flow<UserSession> = userDataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(
                    UserSession(
                        accessToken = "",
                        refreshToken = "",
                        expiresIn = 0,
                        user = null,
                        tokenType = "",
                    )
                )
            }
        }

    suspend fun updateUserSessionPreferences(userSession: UserSession) {
        userDataStore.updateData { session ->
            session.copy(
                userSession.accessToken,
                userSession.refreshToken,
                userSession.providerRefreshToken,
                userSession.providerToken,
                userSession.expiresIn,
                userSession.tokenType,
                userSession.user,
                userSession.tokenType
            )
        }
    }
}