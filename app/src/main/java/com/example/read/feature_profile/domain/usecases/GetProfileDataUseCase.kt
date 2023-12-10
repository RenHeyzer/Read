package com.example.read.feature_profile.domain.usecases

import com.example.read.feature_profile.domain.extensions.handleLatestAsEitherFlow
import com.example.read.feature_profile.domain.models.User
import com.example.read.feature_profile.domain.repositories.ProfileRepository
import kotlinx.coroutines.flow.channelFlow
import javax.inject.Inject

class GetProfileDataUseCase @Inject constructor(
    private val repository: ProfileRepository,
    private val getGuestUsername: GetGuestUsernameUseCase
) {

    operator fun invoke() = channelFlow {
        try {
            val user = repository.getUser()

            if (user != null) {
                send(ProfileResult.Success(user))
            } else {
                getGuestUsername().handleLatestAsEitherFlow(
                    success = {
                        send(ProfileResult.Guest(it))
                    },
                    error = {
                        send(ProfileResult.Error(it))
                    }
                )
            }
        } catch (e: Exception) {
            send(ProfileResult.Error(e.message ?: "Unknown error"))
        }
    }
}

sealed interface ProfileResult {

    class Error(val message: String) : ProfileResult

    class Guest(val username: String) : ProfileResult

    class Success(val user: User) : ProfileResult
}