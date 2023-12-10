package com.example.read.feature_profile.presentation.screens

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.read.feature_profile.domain.models.User
import com.example.read.feature_profile.domain.repositories.ProfileRepository
import com.example.read.feature_profile.domain.usecases.GetProfileDataUseCase
import com.example.read.feature_profile.domain.usecases.ProfileResult
import com.example.read.utils.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getProfileData: GetProfileDataUseCase,
    private val userRepository: ProfileRepository
) : BaseViewModel() {

    private val _profileState = MutableStateFlow<ProfileUiState>(ProfileUiState.Loading)
    val profileState = _profileState.asStateFlow()

    init {
        getProfileInfo()
    }

    fun getProfileInfo() {
        viewModelScope.launch {
            getProfileData().collectLatest {
                when (it) {
                    is ProfileResult.Error -> _profileState.value = ProfileUiState.Error(it.message)
                    is ProfileResult.Guest -> _profileState.value = ProfileUiState.Guest(it.username)
                    is ProfileResult.Success -> _profileState.value = ProfileUiState.Success(it.user)
                }
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            kotlin.runCatching {
                userRepository.logout()
                getProfileInfo()
            }.onSuccess {
                Log.e("logout", "success")
            }.onFailure {
                it.message?.let { message ->
                    Log.e("logout", message)
                }
            }
        }
    }
}

sealed interface ProfileUiState {

    data object Loading : ProfileUiState

    class Error(val message: String) : ProfileUiState

    class Guest(val username: String) : ProfileUiState

    class Success(val user: User) : ProfileUiState
}