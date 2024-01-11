package com.example.read.feature_auth.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.read.common.presentation.base.BaseViewModel
import com.example.read.common.presentation.utils.UiState
import com.example.read.feature_auth.domain.repositories.AuthRepository
import com.example.read.feature_auth.presentation.models.LoginType
import com.example.read.feature_auth.presentation.utils.ExceptionMessages
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository,
) : BaseViewModel() {

    private val _signUpState = MutableStateFlow<UiState<Boolean>>(UiState.Loading())
    val signUpState = _signUpState.asStateFlow()

    private val _signInState = MutableStateFlow<UiState<Boolean>>(UiState.Loading())
    val signInState = _signInState.asStateFlow()

    private val _timerState = MutableStateFlow(false)
    val timerState = _timerState.asStateFlow()

    /*private val _emailConfirmationState = MutableStateFlow<UiState<Nothing>>(UiState.Loading())
    val emailConfirmationState = _emailConfirmationState.asStateFlow()*/

    private fun signUp(params: LoginType.SignUp, exceptionMessages: ExceptionMessages) {
        viewModelScope.launch {
            kotlin.runCatching {
                authRepository.signUp(params.email, params.password)
            }.onSuccess { result ->
                _signUpState.value = UiState.Success(result)
                if (result) signIn(
                    LoginType.SignIn(params.email, params.password),
                    exceptionMessages
                )
            }.onFailure { e ->
                val message = when (e) {
                    is FirebaseAuthUserCollisionException -> exceptionMessages.authUserCollisionExceptionMessage
                    is FirebaseAuthWeakPasswordException -> exceptionMessages.authWeakPasswordExceptionMessage
                    is FirebaseNetworkException -> exceptionMessages.networkExceptionMessage
                    else -> null
                }
                Log.e("signUp", e.message ?: "Unknown error!", e)

                _signUpState.value = UiState.Error(message)
            }
        }
    }

    private fun signIn(params: LoginType.SignIn, exceptionMessages: ExceptionMessages) {
        viewModelScope.launch {
            kotlin.runCatching {
                authRepository.signIn(params.email, params.password)
            }.onSuccess { result ->
                _signInState.value = UiState.Success(result)
            }.onFailure { e ->
                val message = when (e) {
                    is FirebaseAuthInvalidUserException -> exceptionMessages.authInvalidUserExceptionMessage
                    is FirebaseAuthInvalidCredentialsException -> exceptionMessages.authInvalidCredentialsExceptionMessage
                    is FirebaseNetworkException -> exceptionMessages.networkExceptionMessage
                    is FirebaseTooManyRequestsException -> exceptionMessages.tooManyRequestsExceptionMessage
                    else -> null
                }
                Log.e("signIn", e.message ?: "Unknown error!", e)

                _signInState.value = UiState.Error(message)
            }
        }
    }

    fun login(type: LoginType, exceptionMessages: ExceptionMessages) {
        when (type) {
            LoginType.Google -> {}
            is LoginType.SignIn -> signIn(type, exceptionMessages)
            is LoginType.SignUp -> signUp(type, exceptionMessages)
        }
    }

    val isEmailVerified: Boolean
        get() = authRepository.isEmailVerified

    /*fun confirmEmail(email: String) {
        viewModelScope.launch {
            kotlin.runCatching {
                authRepository.confirmEmail(email)
            }.onSuccess {
                _emailConfirmationState.value = UiState.Success()
            }.onFailure { t ->
                val message = t.message ?: "Unknown error!"
                Log.e("confirmEmail", message, t)

                _emailConfirmationState.value = UiState.Error(message)
            }
        }
    }*/
}