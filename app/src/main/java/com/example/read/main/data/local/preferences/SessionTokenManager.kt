package com.example.read.main.data.local.preferences

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.read.utils.constants.Constants
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore by preferencesDataStore(Constants.SESSION_TOKEN_PREFERENCES_NAME)

private object SessionTokenPreferencesKey {
    val ACCESS_TOKEN_KEY = stringPreferencesKey("access_token")
    val REFRESH_TOKEN_KEY = stringPreferencesKey("refresh_token")
}

@Singleton
class SessionTokenManager @Inject constructor(
    @ApplicationContext context: Context
) {

    private val userDataStore = context.dataStore

    val sessionTokensFlow: Flow<TokenPreferences> = userDataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            }
        }.map { preferences ->
            val accessToken = preferences[SessionTokenPreferencesKey.ACCESS_TOKEN_KEY] ?: ""
            val refreshToken = preferences[SessionTokenPreferencesKey.REFRESH_TOKEN_KEY] ?: ""
            TokenPreferences(accessToken = accessToken, refreshToken = refreshToken)
        }

    suspend fun setSessionTokens(tokenPreferences: TokenPreferences) {
        userDataStore.edit { preferences ->
            preferences[SessionTokenPreferencesKey.ACCESS_TOKEN_KEY] =
                tokenPreferences.accessToken
            preferences[SessionTokenPreferencesKey.REFRESH_TOKEN_KEY] =
                tokenPreferences.refreshToken
        }
    }
}