package com.example.read.feature_auth.presentation.utils

import android.content.Context
import com.example.read.R
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class ExceptionMessages @Inject constructor(@ApplicationContext private val context: Context) {

    val authInvalidUserExceptionMessage =
        context.getString(R.string.auth_invalid_user_exception_message)
    val authInvalidCredentialsExceptionMessage =
        context.getString(R.string.auth_invalid_credentials_exception_message)
    val networkExceptionMessage = context.getString(R.string.network_exception_message)
    val tooManyRequestsExceptionMessage =
        context.getString(R.string.too_many_requests_exception_message)
    val authUserCollisionExceptionMessage =
        context.getString(R.string.auth_user_collision_exception_message)
    val authWeakPasswordExceptionMessage =
        context.getString(R.string.auth_weak_password_exception_message)
}