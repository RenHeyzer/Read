package com.example.read.feature_profile.presentation.screens

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.read.R
import com.example.read.main.presentation.Screen
import com.example.read.ui.theme.PurpleVerticalToBottom
import com.example.read.ui.theme.ReadTheme
import com.example.read.ui.theme.Rubik
import com.example.read.utils.extensions.LoadingIndicator

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    viewModel: ProfileViewModel = hiltViewModel(),
    navController: NavHostController,
) {
    val profileState by viewModel.profileState.collectAsState()

    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        when (profileState) {
            is ProfileUiState.Loading -> {
                LoadingIndicator()
            }

            is ProfileUiState.Error -> {
                Log.e("getUser", (profileState as ProfileUiState.Error).message)
            }

            is ProfileUiState.Success -> {
                with((profileState as ProfileUiState.Success).user.metadata) {
                    UserInfo(
                        username = username,
                        profileImage = profileImage,
                    ) {
                        OutlinedButton(
                            modifier = Modifier
                                .align(Alignment.BottomEnd)
                                .padding(bottom = 20.dp, end = 20.dp),
                            onClick = {
                                viewModel.logout()
                            },
                            shape = RoundedCornerShape(10.dp),
                            border = BorderStroke(1.dp, Color.White)
                        ) {
                            Text(
                                text = stringResource(id = R.string.logout_button_text),
                                color = Color.White,
                                fontSize = 14.sp,
                                fontFamily = Rubik,
                                fontWeight = FontWeight.Bold,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }
                }
            }

            is ProfileUiState.Guest -> {
                with(profileState as ProfileUiState.Guest) {
                    UserInfo(
                        guestUsername = username
                    ) {
                        OutlinedButton(
                            modifier = Modifier
                                .align(Alignment.BottomEnd)
                                .padding(bottom = 20.dp, end = 20.dp),
                            onClick = {
                                navController.navigate(Screen.SignIn.route)
                            },
                            shape = RoundedCornerShape(10.dp),
                            border = BorderStroke(1.dp, Color.White)
                        ) {
                            Text(
                                text = stringResource(id = R.string.sign_in_button_text),
                                color = Color.White,
                                fontSize = 14.sp,
                                fontFamily = Rubik,
                                fontWeight = FontWeight.Bold,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewProfile() {
    ReadTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            ProfileScreen(navController = rememberNavController())
        }
    }
}

@Composable
fun UserInfo(
    username: String? = null,
    profileImage: String? = null,
    guestUsername: String? = null,
    button: (@Composable BoxScope.() -> Unit)? = null
) {
    Card(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(10.dp),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(PurpleVerticalToBottom),
        ) {
            Row {
                AsyncImage(
                    modifier = Modifier
                        .padding(10.dp)
                        .width(100.dp)
                        .height(100.dp)
                        .clip(CircleShape)
                        .align(Alignment.CenterVertically),
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(profileImage)
                        .crossfade(true)
                        .build(),
                    filterQuality = FilterQuality.High,
                    error = painterResource(id = R.drawable.profile_image_placeholder),
                    fallback = painterResource(id = R.drawable.profile_image_placeholder),
                    contentDescription = stringResource(R.string.profile_image_content_description),
                    contentScale = ContentScale.FillBounds,
                )
                if (username != null) {
                    Text(
                        modifier = Modifier.padding(top = 20.dp, end = 20.dp),
                        text = username,
                        color = Color.White,
                        fontSize = 18.sp,
                        fontFamily = Rubik,
                        fontWeight = FontWeight.Medium,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                } else {
                    guestUsername?.let {
                        Text(
                            modifier = Modifier.padding(top = 20.dp, end = 20.dp),
                            text = it,
                            color = Color.White,
                            fontSize = 20.sp,
                            fontFamily = Rubik,
                            fontWeight = FontWeight.Medium,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }
            button?.let {
                it()
            }
        }
    }
}