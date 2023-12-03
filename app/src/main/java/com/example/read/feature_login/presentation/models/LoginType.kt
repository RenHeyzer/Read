package com.example.read.feature_login.presentation.models

sealed interface LoginType {

    data class SignUp(
        val username: String,
        val email: String,
        val password: String,
        val confirmPassword: String
    ) : LoginType {
        fun validateFields(
            onSuccess: () -> Unit,
            onError: (empty: Empty) -> Unit
        ) {
            val username = username.trim()
            val email = email.trim()
            val password = password.trim()
            val confirmPassword = confirmPassword.trim()

            val empty = Empty(
                username = username.isEmpty(),
                email = email.isEmpty(),
                password = password.isEmpty(),
                confirmPassword = confirmPassword.isEmpty()
            )
            if (username.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                onError(
                    empty
                )
            }
            if (username.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty() && confirmPassword.isNotEmpty()) {
                if (confirmPassword != password) {
                    onError(
                        empty.copy(
                            notEqualsPasswords = true
                        )
                    )
                } else {
                    onSuccess()
                }
            } else {
                onError(
                    empty
                )
            }
        }
    }

    data class Login(
        val email: String,
        val password: String,
    ) : LoginType {
        fun validateFields(
            onSuccess: () -> Unit,
            onError: (empty: Empty) -> Unit
        ) {
            val email = email.trim()
            val password = password.trim()
            val empty = Empty(email = email.isEmpty(), password = password.isEmpty())

            if (email.isEmpty() || password.isEmpty()) {
                onError(empty)
            }
            if (email.isNotEmpty() && password.isNotEmpty()) {
                onSuccess()
            } else {
                onError(empty)
            }
        }
    }

    data object Google : LoginType
}