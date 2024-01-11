package com.example.read.feature_auth.presentation.utils

import androidx.fragment.app.Fragment
import com.example.read.R
import com.example.read.feature_auth.presentation.models.EmptyProperties
import com.example.read.feature_auth.presentation.models.LoginType
import com.google.android.material.textfield.TextInputLayout

fun validateProperties(
    type: LoginType
): ValidatePropertiesState {
    return when (type) {
        LoginType.Google -> throw IllegalStateException("Illegal login type")
        is LoginType.SignIn -> {
            with(type) {
                val empty =
                    EmptyProperties(email = email.isEmpty(), password = password.isEmpty())

                if (email.isEmpty() || password.isEmpty()) {
                    return ValidatePropertiesState.Error(empty)
                }
                return if (email.isNotEmpty() && password.isNotEmpty()) {
                    ValidatePropertiesState.Success(type)
                } else {
                    ValidatePropertiesState.Error(empty)
                }
            }
        }

        is LoginType.SignUp -> {
            with(type) {
                val empty = EmptyProperties(
                    username = username.isEmpty(),
                    email = email.isEmpty(),
                    password = password.isEmpty(),
                    confirmPassword = confirmPassword.isEmpty()
                )
                if (username.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                    return ValidatePropertiesState.Error(empty)
                }
                return if (username.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty() && confirmPassword.isNotEmpty()) {
                    return if (confirmPassword != password) {
                        ValidatePropertiesState.Error(
                            empty.copy(
                                notEqualsPasswords = true
                            )
                        )
                    } else {
                        ValidatePropertiesState.Success(type)
                    }
                } else {
                    ValidatePropertiesState.Error(empty)
                }
            }
        }
    }
}

fun Fragment.enableTextInputLayoutsError(
    tilUsername: TextInputLayout,
    tilEmail: TextInputLayout,
    tilPassword: TextInputLayout,
    tilConfirmPassword: TextInputLayout,
    empty: EmptyProperties
) {
    tilUsername.isErrorEnabled = empty.username
    tilEmail.isErrorEnabled = empty.email
    tilPassword.isErrorEnabled =
        empty.password || empty.notEqualsPasswords
    tilConfirmPassword.isErrorEnabled =
        empty.confirmPassword || empty.notEqualsPasswords

    if (tilUsername.isErrorEnabled) {
        tilUsername.error = getString(R.string.empty_text_field_error)
    }
    if (tilEmail.isErrorEnabled) {
        tilEmail.error = getString(R.string.empty_text_field_error)
    }
    if (tilPassword.isErrorEnabled) {
        tilPassword.error =
            if (empty.notEqualsPasswords) getString(R.string.passwords_not_equals_text_field_error)
            else getString(R.string.empty_text_field_error)
    }
    if (tilConfirmPassword.isErrorEnabled) {
        tilConfirmPassword.error =
            if (empty.notEqualsPasswords) getString(R.string.passwords_not_equals_text_field_error)
            else getString(R.string.empty_text_field_error)
    }
}