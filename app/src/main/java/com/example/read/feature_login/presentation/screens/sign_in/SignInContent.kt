package com.example.read.feature_login.presentation.screens.sign_in

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
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
fun SignInContent(
    modifier: Modifier = Modifier,
    isSendConfirmation: Boolean,
    timeUntilSendingConfirmation: Int,
    isSignInButtonPressed: Boolean,
    login: ((LoginType) -> Unit)? = null,
    singUp: () -> Unit,
    resendEmailConfirmation: (() -> Unit)? = null
) {
    var email by rememberSaveable {
        mutableStateOf("" to false)
    }
    var password by rememberSaveable {
        mutableStateOf("" to false)
    }

    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            modifier = Modifier
                .padding(top = 100.dp)
                .align(Alignment.CenterHorizontally),
            text = stringResource(id = R.string.screen_sign_in),
            color = Color.Black,
            fontSize = 26.sp,
            fontFamily = Rubik,
            fontWeight = FontWeight.ExtraBold,
        )

        Spacer(modifier = Modifier.weight(0.2f))

        OutlinedErrorTextField(modifier = Modifier
            .padding(horizontal = 30.dp)
            .fillMaxWidth(),
            value = email.first,
            onValueChange = {
                email = it to false
            },
            errorText = stringResource(R.string.empty_text_field_error_text),
            isError = email.second,
            placeholder = {
                Text(text = stringResource(R.string.email_text_field_placeholder))
            })

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedErrorTextField(modifier = Modifier
            .padding(horizontal = 30.dp)
            .fillMaxWidth(),
            value = password.first,
            onValueChange = {
                password = it to false
            },
            isError = password.second,
            errorText = stringResource(R.string.empty_text_field_error_text),
            placeholder = {
                Text(text = stringResource(R.string.password_text_field_placeholder))
            })

        Spacer(modifier = Modifier.weight(0.1f))

        if (!isSendConfirmation) {
            if (!isSignInButtonPressed) {
                ElevatedButton(
                    modifier = Modifier
                        .padding(start = 30.dp, end = 30.dp)
                        .fillMaxWidth()
                        .height(50.dp),
                    onClick = {
                        if (login != null) {
                            LoginType.Login(email.first, password.first).apply {
                                validateFields(
                                    onSuccess = {
                                        login(this)
                                    },
                                    onError = { empty ->
                                        email = this.email to empty.email
                                        password = this.password to empty.password
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
                            text = stringResource(id = R.string.sign_in_button_text),
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

        ElevatedButton(
            modifier = Modifier
                .padding(start = 30.dp, end = 30.dp)
                .fillMaxWidth(),
            onClick = {
                if (login != null) {
                    login(LoginType.Google)
                }
            },
            elevation = ButtonDefaults.elevatedButtonElevation(defaultElevation = 4.dp),
            colors = ButtonDefaults.elevatedButtonColors(
                containerColor = Color.White,
            ),
            shape = RoundedCornerShape(10.dp),
            border = BorderStroke(1.dp, Purple60)
        ) {
            Icon(
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(bottom = 2.dp),
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_google),
                contentDescription = stringResource(
                    id = R.string.google_icon_content_description
                ),
                tint = Color.Unspecified
            )

            Spacer(modifier = Modifier.width(6.dp))

            Text(
                text = stringResource(id = R.string.sign_in_with_google_button_text),
                color = Color.Black,
                fontSize = 18.sp,
                fontFamily = Rubik,
                fontWeight = FontWeight.Bold,
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            modifier = Modifier.clickable {
                singUp()
            },
            text = stringResource(id = R.string.sign_up_button_text),
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
fun PreviewSignInContent() {
    ReadTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            SignInContent(
                modifier = Modifier
                    .fillMaxSize(),
                isSendConfirmation = false,
                timeUntilSendingConfirmation = 0,
                singUp = {},
                isSignInButtonPressed = false,
            )
        }
    }
}