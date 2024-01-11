package com.example.read.feature_auth.presentation.utils

import com.example.read.feature_auth.presentation.models.EmptyProperties
import com.example.read.feature_auth.presentation.models.LoginType

sealed interface ValidatePropertiesState {

    class Error(val empty: EmptyProperties): ValidatePropertiesState

    class Success(val type: LoginType) : ValidatePropertiesState
}