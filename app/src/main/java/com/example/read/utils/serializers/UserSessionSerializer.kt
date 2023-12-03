package com.example.read.utils.serializers

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import io.github.jan.supabase.gotrue.user.UserSession
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream

object UserSessionSerializer :
    Serializer<UserSession> {

    override val defaultValue: UserSession = UserSession(
        accessToken = "",
        refreshToken = "",
        expiresIn = 0,
        user = null,
        tokenType = "",
    )

    override suspend fun readFrom(input: InputStream): UserSession {
        try {
            return Json.decodeFromString(
                UserSession.serializer(), input.readBytes().decodeToString()
            )
        } catch (e: SerializationException) {
            throw CorruptionException("Unable to read User", e)
        }
    }

    override suspend fun writeTo(t: UserSession, output: OutputStream) {
        withContext(Dispatchers.IO) {
            output.write(
                Json.encodeToString(UserSession.serializer(), t).encodeToByteArray()
            )
        }
    }
}