package com.example.read.feature_login.presentation.screens.sign_up

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.read.R
import com.example.read.feature_login.presentation.screens.LoginViewModel
import com.example.read.utils.extensions.countDownTimer

@Composable
fun SignUpScreen(
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel = hiltViewModel(),
    navController: NavHostController,
    snackbarHostState: SnackbarHostState
) {

    var isSendConfirmation by rememberSaveable {
        mutableStateOf(false)
    }

    val snackbarMessage = stringResource(id = R.string.confirmation_message_sent)
    LaunchedEffect(isSendConfirmation) {
        if (isSendConfirmation) {
            snackbarHostState.showSnackbar(
                message = snackbarMessage,
                duration = SnackbarDuration.Short
            )
        }
    }

    var timeUntilSendingConfirmation by rememberSaveable {
        mutableIntStateOf(60)
    }
    var isSignUpButtonPressed by rememberSaveable {
        mutableStateOf(false)
    }

    val confirmationTimer = countDownTimer(
        millsInFuture = 60000,
        countDownInterval = 1000,
        onFinish = {
            isSendConfirmation = false
            timeUntilSendingConfirmation = 60
        },
        onTick = {
            timeUntilSendingConfirmation -= 1
        }
    )

    SignUpContent(
        modifier = modifier,
        isSendConfirmation = isSendConfirmation,
        timeUntilSendingConfirmation = timeUntilSendingConfirmation,
        isSignUpButtonPressed = isSignUpButtonPressed,
        signUp = { singUp ->
            with(singUp) {
                viewModel.signUp(
                    username = username.trim(),
                    email = email.trim(),
                    password = password.trim(),
                    onSuccess = {
                        confirmationTimer.start()
                        isSendConfirmation = true
                        isSignUpButtonPressed = true
                    },
                    onFailure = {

                    }
                )
            }
        },
        signIn = {
            navController.navigateUp()
        },
        resendEmailConfirmation = {
            viewModel.resendEmailConfirmation(onSuccess = {
                confirmationTimer.start()
                isSendConfirmation = true
            })
        }
    )
}

