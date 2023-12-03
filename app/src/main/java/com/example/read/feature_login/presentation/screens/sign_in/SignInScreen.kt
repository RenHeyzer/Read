package com.example.read.feature_login.presentation.screens.sign_in

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
import com.example.read.feature_login.presentation.models.LoginType
import com.example.read.feature_login.presentation.screens.LoginViewModel
import com.example.read.main.presentation.Screen
import com.example.read.utils.extensions.countDownTimer

@Composable
fun SignInScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    viewModel: LoginViewModel = hiltViewModel(),
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
    var isSignInButtonPressed by rememberSaveable {
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

    SignInContent(
        modifier = modifier,
        isSendConfirmation = isSendConfirmation,
        timeUntilSendingConfirmation = timeUntilSendingConfirmation,
        isSignInButtonPressed = isSignInButtonPressed,
        login = { login ->
            if (login is LoginType.Login) {
                with(login) {
                    viewModel.login(
                        email = email.trim(),
                        password = password.trim(),
                        onSuccess = {
                            viewModel.sendOtpToEmail(email.trim()) {
                                confirmationTimer.start()
                                isSendConfirmation = true
                                isSignInButtonPressed = true
                            }
                        },
                        onFailure = {

                        }
                    )
                }
            }
            if (login is LoginType.Google) {

            }
        },
        singUp = {
            navController.navigate(Screen.SignUp.route)
        },
        resendEmailConfirmation = {
            viewModel.resendEmailConfirmation(onSuccess = {
                confirmationTimer.start()
                isSendConfirmation = true
            })
        }
    )
}