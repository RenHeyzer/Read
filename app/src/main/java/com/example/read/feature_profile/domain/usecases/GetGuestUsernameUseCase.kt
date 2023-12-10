package com.example.read.feature_profile.domain.usecases

import com.example.read.feature_profile.domain.repositories.ProfileRepository
import com.example.read.utils.state_holders.Either
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

class GetGuestUsernameUseCase @Inject constructor(
    private val repository: ProfileRepository
) {

    operator fun invoke() = channelFlow {
        val id = (1000..9999).random()
        try {
            repository.guestUsernameFlow.collectLatest { username ->
                if (username.isEmpty()) {
                    repository.updateGuestUsername("Пользователь$id")
                } else {
                    send(Either.Right(username))
                }
            }
        } catch (e: Exception) {
            send(Either.Left(e.message ?: "Unknown error!"))
        }
    }
}