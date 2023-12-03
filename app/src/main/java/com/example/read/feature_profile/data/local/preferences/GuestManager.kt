package com.example.read.feature_profile.data.local.preferences

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

private val Context.dataStore by preferencesDataStore(Constants.GUEST_PREFERENCES_NAME)

@Singleton
class GuestManager @Inject constructor(
    @ApplicationContext context: Context
) {

    private val guestDataStore = context.dataStore

    val guestUsernameFlow: Flow<String> = guestDataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                exception.printStackTrace()
            }
        }.map { preferences ->
            preferences[GUEST_USERNAME] ?: ""
        }

    suspend fun updateQuestUsername(username: String) {
        guestDataStore.edit { preferences ->
            preferences[GUEST_USERNAME] = username
        }
    }

    companion object {
        private val GUEST_USERNAME = stringPreferencesKey("guest_username")
    }
}