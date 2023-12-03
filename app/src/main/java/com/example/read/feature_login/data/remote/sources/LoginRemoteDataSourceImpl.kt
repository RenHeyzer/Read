package com.example.read.feature_login.data.remote.sources

import com.example.read.feature_login.domain.models.SignUpParams
import io.github.jan.supabase.gotrue.GoTrue
import io.github.jan.supabase.gotrue.OtpType
import io.github.jan.supabase.gotrue.providers.builtin.Email
import io.github.jan.supabase.postgrest.Postgrest
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put
import javax.inject.Inject

class LoginRemoteDataSourceImpl @Inject constructor(
    private val gotrue: GoTrue,
    private val postgrest: Postgrest,
) : LoginRemoteDataSource {

    override suspend fun signUp(
        params: SignUpParams
    ): Email.Result {
        val user = gotrue.signUpWith(Email) {
            this.email = params.email
            this.password = params.password
            data = buildJsonObject {
                put("username", params.username)
                put("profile_image", params.profileImage)
            }
        }
        return checkNotNull(user)
    }

    override suspend fun login(email: String, password: String) {
        gotrue.loginWith(Email) {
            this.email = email
            this.password = password
        }
    }

    override suspend fun loginWithGoogle() {

    }

    override suspend fun sendOtpToEmail(email: String) {
        gotrue.sendOtpTo(Email) {
            this.email = email
        }
    }

    /*override suspend fun createUser(userDto: UserDto) {
        when(gotrue.sessionStatus.value) {
            is SessionStatus.Authenticated -> {
                postgrest[Constants.USERS_TABLE_NAME].insert(userDto)
            }
            else -> {
                throw IllegalStateException("Not authenticated")
            }
        }
    }*/

    override suspend fun resendEmailConfirmation(email: String) {
        gotrue.resendEmail(OtpType.Email.SIGNUP, email)
    }
}