package com.example.read.feature_login.presentation.screens.sign_up

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layout
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.read.R
import com.example.read.feature_login.presentation.models.LoginType
import com.example.read.ui.theme.Purple50
import com.example.read.ui.theme.Purple60
import com.example.read.ui.theme.PurpleVerticalToBottom
import com.example.read.ui.theme.ReadTheme
import com.example.read.ui.theme.Rubik
import com.example.read.utils.extensions.OutlinedErrorTextField

@Composable
fun SignUpContent(
    modifier: Modifier = Modifier,
    isSendConfirmation: Boolean,
    timeUntilSendingConfirmation: Int,
    isSignUpButtonPressed: Boolean,
    signUp: ((LoginType.SignUp) -> Unit)? = null,
    signIn: () -> Unit,
    resendEmailConfirmation: (() -> Unit)? = null
) {
    var username by rememberSaveable {
        mutableStateOf("" to false)
    }
    var email by rememberSaveable {
        mutableStateOf("" to false)
    }
    var password by rememberSaveable {
        mutableStateOf("" to false)
    }
    var confirmPassword by rememberSaveable {
        mutableStateOf("" to false)
    }
    var notEqualsPasswords by rememberSaveable {
        mutableStateOf(false)
    }

    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            modifier = Modifier
                .padding(top = 100.dp)
                .align(Alignment.CenterHorizontally),
            text = stringResource(id = R.string.screen_sign_up),
            color = Color.Black,
            fontSize = 26.sp,
            fontFamily = Rubik,
            fontWeight = FontWeight.ExtraBold,
        )

        Spacer(modifier = Modifier.weight(0.1f))

        OutlinedErrorTextField(modifier = Modifier
            .padding(horizontal = 30.dp)
            .fillMaxWidth(),
            value = username.first,
            onValueChange = {
                username = it to false
            },
            errorText = stringResource(R.string.empty_text_field_error_text),
            isError = username.second,
            label = {
                Text(text = stringResource(R.string.username_text_field_label))
            })

        OutlinedErrorTextField(modifier = Modifier
            .padding(horizontal = 30.dp)
            .fillMaxWidth(),
            value = email.first,
            onValueChange = {
                email = it to false
            },
            errorText = stringResource(R.string.empty_text_field_error_text),
            isError = email.second,
            label = {
                Text(text = stringResource(R.string.email_text_field_label))
            })

        OutlinedErrorTextField(modifier = Modifier
            .padding(horizontal = 30.dp)
            .fillMaxWidth(),
            value = password.first,
            onValueChange = {
                password = it to false
            },
            isError = password.second,
            errorText = stringResource(R.string.empty_text_field_error_text),
            label = {
                Text(text = stringResource(R.string.password_text_field_label))
            })

        OutlinedErrorTextField(
            modifier = Modifier
                .padding(horizontal = 30.dp)
                .fillMaxWidth(),
            value = confirmPassword.first,
            onValueChange = {
                confirmPassword = it to false
                notEqualsPasswords = false
            },
            errorText = if (confirmPassword.second) stringResource(R.string.empty_text_field_error_text)
            else stringResource(R.string.passwords_not_equals_text_field_error_text),
            isError = confirmPassword.second or notEqualsPasswords,
            label = {
                Text(text = stringResource(R.string.confirm_password_text_field_label))
            },
        )

        if (!isSendConfirmation) {
            if (!isSignUpButtonPressed) {
                ElevatedButton(
                    modifier = Modifier
                        .padding(start = 30.dp, end = 30.dp, top = 40.dp)
                        .fillMaxWidth()
                        .height(50.dp),
                    onClick = {
                        if (signUp != null) {
                            LoginType.SignUp(
                                username.first,
                                email.first,
                                password.first,
                                confirmPassword.first
                            ).apply {
                                validateFields(
                                    onSuccess = {
                                        signUp(this)
                                    },
                                    onError = { empty ->
                                        username = this.username to empty.username
                                        email = this.email to empty.email
                                        password = this.password to empty.password
                                        confirmPassword =
                                            this.confirmPassword to empty.confirmPassword
                                        notEqualsPasswords = empty.notEqualsPasswords
                                    }
                                )
                            }
                        }
                    },
                    elevation = ButtonDefaults.elevatedButtonElevation(defaultElevation = 4.dp),
                    shape = RoundedCornerShape(10.dp),
                    border = BorderStroke(1.dp, Purple60),
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(PurpleVerticalToBottom),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = stringResource(id = R.string.sign_up_button_text),
                            color = Color.White,
                            fontSize = 18.sp,
                            fontFamily = Rubik,
                            fontWeight = FontWeight.ExtraBold,
                        )
                    }
                }
            } else {
                Text(
                    modifier = Modifier
                        .padding(start = 30.dp, end = 30.dp, top = 40.dp)
                        .fillMaxWidth()
                        .align(Alignment.CenterHorizontally)
                        .clickable {
                            if (resendEmailConfirmation != null) {
                                resendEmailConfirmation()
                            }
                        },
                    text = stringResource(id = R.string.send_confirmation_again),
                    color = Purple50,
                    fontSize = 14.sp,
                    fontFamily = Rubik,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Medium,
                )
            }
        } else {
            if (timeUntilSendingConfirmation != 0) {
                Text(
                    modifier = Modifier
                        .padding(start = 30.dp, end = 30.dp, top = 40.dp)
                        .fillMaxWidth()
                        .align(Alignment.CenterHorizontally),
                    text = stringResource(
                        id = R.string.until_sending_confirmation,
                        timeUntilSendingConfirmation
                    ),
                    color = Color.Black,
                    fontSize = 14.sp,
                    fontFamily = Rubik,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Medium,
                )
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            modifier = Modifier.clickable {
                signIn()
            },
            text = stringResource(id = R.string.already_hava_an_account_text),
            color = Purple50,
            fontSize = 12.sp,
            fontFamily = Rubik,
            fontWeight = FontWeight.Normal,
        )

        Spacer(modifier = Modifier.weight(0.2f))
    }
}

@Preview
@Composable
fun PreviewSignUpContent() {
    ReadTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            SignUpContent(
                modifier = Modifier
                    .fillMaxSize(),
                isSendConfirmation = false,
                timeUntilSendingConfirmation = 0,
                signIn = {},
                isSignUpButtonPressed = false,
            )
        }
    }
}