package com.example.read.feature_login.presentation.screens

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.read.feature_login.domain.models.SignUpParams
import com.example.read.feature_login.domain.models.SignUpResult
import com.example.read.feature_login.domain.repositories.LoginRepository
import com.example.read.utils.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val signRepository: LoginRepository
) : BaseViewModel() {

    private val _emailState = MutableStateFlow("")

    fun signUp(
        username: String,
        email: String,
        password: String,
        onSuccess: (result: SignUpResult) -> Unit,
        onFailure: () -> Unit,
    ) {
        viewModelScope.launch {
            kotlin.runCatching {
                signRepository.signUp(SignUpParams(username = username, email = email, password = password))
            }.onSuccess { result ->
                onSuccess(result)
                result.email?.let { email ->
                    _emailState.value = email
                }
                Log.e("signUp", result.toString())
            }.onFailure {
                it.message?.let { message -> Log.e("signUp", message) }
                onFailure()
            }
        }
    }

    fun login(
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onFailure: (message: String) -> Unit
    ) {
        viewModelScope.launch {
            kotlin.runCatching {
                signRepository.login(email, password)
            }.onSuccess {
                Log.e("login", "success")
                onSuccess()
            }.onFailure {
                it.message?.let { message ->
                    Log.e("login", message)
                    onFailure(message)
                }
            }
        }
    }

    /*fun loginWithGoogle() {
        viewModelScope.launch {
            kotlin.runCatching {
                signRepository.loginWithGoogle()
            }.onSuccess {
                Log.e("loginWithGoogle", "success")
            }.onFailure {
                it.message?.let { message -> Log.e("loginWithGoogle", message) }
            }
        }
    }*/

    fun resendEmailConfirmation(onSuccess: () -> Unit) {
        viewModelScope.launch {
            _emailState.collect { email ->
                launch {
                    kotlin.runCatching {
                        signRepository.resendEmailConfirmation(email)
                    }.onSuccess {
                        Log.e("resend", "success")
                        onSuccess()
                    }.onFailure {
                        it.message?.let { message -> Log.e("resend", message) }
                    }
                }
            }
        }
    }

    fun sendOtpToEmail(email: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            kotlin.runCatching {
                signRepository.sentOtpToEmail(email)
            }.onSuccess {
                onSuccess()
            }.onFailure {
                it.message?.let { message ->
                    Log.e("sendOtp", message)
                }
            }
        }
    }
}