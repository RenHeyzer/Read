package com.example.read.feature_profile.presentation.screens

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.read.feature_profile.domain.models.User
import com.example.read.feature_profile.domain.models.UserSession
import com.example.read.feature_profile.domain.repositories.UserRepository
import com.example.read.utils.state_holders.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userRepository: UserRepository,
) : ViewModel() {

    private val _userState = MutableStateFlow<UserUiState>(UserUiState.Loading)
    val userState = _userState.asStateFlow()

    private val _guestUsernameState = MutableStateFlow("Пользователь0")
    val guestUsernameState = _guestUsernameState.asStateFlow()

    init {
        getUser()
    }

    fun getUser() {
        viewModelScope.launch {
            kotlin.runCatching {
                userRepository.getUser()
            }.onSuccess { user ->
                if (user != null) {
                    _userState.value = UserUiState.SessionUser(
                        data = user,
                    )
                } else {
                    getRefreshedUser()
                }
            }.onFailure {
                it.message?.let { message ->
                    UiState.Error<User>(message)
                }
            }
        }
    }

    fun getRefreshedUser() {
        viewModelScope.launch {
            userRepository.userSessionFlow.collectLatest { session ->
                if (session.refreshToken.isNotEmpty()) {
                    launch {
                        kotlin.runCatching {
                            userRepository.getRefreshedUser(session.refreshToken)
                        }.onSuccess { session ->
                            session.user?.let { user ->
                                _userState.value = UserUiState.RefreshedUser(user)
                            }
                        }.onFailure {
                            _userState.value = UserUiState.Error(it.message)
                        }
                    }
                } else {
                    getGuestUsername()
                }
            }
        }
    }

    fun getGuestUsername() {
        viewModelScope.launch {
            userRepository.guestUsernameFlow.collectLatest { username ->
                _userState.value = UserUiState.Guest(username)
            }
        }
    }

    fun updateGuestUsername() {
        viewModelScope.launch {
            val id = (1000..9999).random()
            userRepository.updateGuestUsername("Пользователь$id")
        }
    }

    fun logout() {
        viewModelScope.launch {
            kotlin.runCatching {
                userRepository.logout()
                userRepository.updateUserSession(
                    UserSession(
                        accessToken = "",
                        refreshToken = "",
                        expiresIn = 0,
                        user = null,
                        tokenType = "",
                    )
                )
            }.onSuccess {
                Log.e("logout", "success")
                getUser()
            }.onFailure {
                it.message?.let { message ->
                    Log.e("logout", message)
                }
            }
        }
    }
}

sealed interface UserUiState {

    data object Loading : UserUiState

    class Error(val message: String? = null) : UserUiState

    class Guest(val username: String = "Пользователь0") : UserUiState
    class SessionUser(val data: User) : UserUiState
    class RefreshedUser(val data: User) : UserUiState
}