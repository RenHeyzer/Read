package com.example.read.feature_detail.presentation.screens

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.BookmarkAdd
import androidx.compose.material.icons.sharp.StarRate
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.read.R
import com.example.read.feature_detail.domain.models.Info
import com.example.read.ui.theme.DarkPurpleVertical
import com.example.read.ui.theme.Purple90
import com.example.read.ui.theme.PurpleVertical
import com.example.read.ui.theme.WhiteVertical
import com.example.read.ui.theme.Yellow
import com.example.read.ui.theme.rubikFamily
import com.example.read.utils.state_holders.UiState

@Composable
fun DetailScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: BookInfoViewModel = hiltViewModel()
) {

    val state = viewModel.infoState.collectAsState()
    when (state.value) {
        is UiState.Error -> {
            (state.value as UiState.Error<Info>).message?.let {
                Log.e("error", it)
            }
        }

        is UiState.Loading -> {
            Bootstrap()
        }

        is UiState.Success -> {
            (state.value as UiState.Success<Info>).data?.let { info ->
                Log.e("success", "DetailScreen: $info")
                DetailScreenContent(modifier = modifier, info = info, onBackPressed = {
                    navController.navigateUp()
                })
            }
        }
    }
}

@Composable
fun Bootstrap() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator(color = Purple90)
    }
}

@Preview
@Composable
fun PreviewBootstrap() {
    Bootstrap()
}

@Composable
fun DetailScreenContent(modifier: Modifier = Modifier, info: Info, onBackPressed: () -> Unit) {

    Column(modifier) {

        TopContent(
            modifier = Modifier
                .wrapContentWidth()
                .height(0.dp)
                .weight(1.4f),
            info = info,
            onBackPressed = onBackPressed
        )

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(10.dp)
        )

        MiddleContent(
            modifier = Modifier
                .fillMaxWidth()
                .height(0.dp)
                .padding(horizontal = 4.dp)
                .weight(2.1f),
            info = info
        )

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(10.dp)
        )

        BottomContent(
            modifier = Modifier
                .fillMaxWidth()
                .height(0.dp)
                .padding(horizontal = 4.dp)
                .weight(0.3f)
        )

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(10.dp)
        )
    }
}

@Composable
fun TopContent(modifier: Modifier = Modifier, info: Info, onBackPressed: () -> Unit) = with(info) {

    Box(
        modifier = modifier
    ) {
        BackButton(Modifier
            .align(Alignment.TopStart), onClick = {
            onBackPressed()
        })

        BookmarkButton(Modifier
            .align(Alignment.TopEnd), onClick = {

        })

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(top = 50.dp, start = 4.dp, end = 4.dp),
            shape = RoundedCornerShape(20.dp),
            border = BorderStroke(2.dp, brush = DarkPurpleVertical)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(brush = PurpleVertical),
            ) {

                rating?.let {
                    Rating(
                        modifier = Modifier
                            .align(Alignment.End)
                            .padding(top = 10.dp, end = 20.dp),
                        rating = it.toString()
                    )
                }
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(0.dp)
                        .weight(1f)
                )

                info.title?.let {
                    Text(
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        text = it,
                        fontFamily = rubikFamily,
                        fontWeight = FontWeight.Medium,
                        fontStyle = FontStyle.Normal,
                        fontSize = 18.sp,
                        color = Color.White,
                    )
                }

                if (releaseYear != null || type != null || status != null) {
                    Text(
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        text = "$releaseYear $type $status",
                        fontFamily = rubikFamily,
                        fontWeight = FontWeight.Medium,
                        fontStyle = FontStyle.Normal,
                        fontSize = 12.sp,
                        color = Color.Gray,
                    )
                }
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(10.dp)
                )
            }
        }

        AsyncImage(
            modifier = Modifier
                .padding(top = 20.dp)
                .width(124.dp)
                .height(184.dp)
                .align(Alignment.TopCenter)
                .clip(RoundedCornerShape(20.dp))
                .border(
                    BorderStroke(
                        width = 4.dp,
                        brush = DarkPurpleVertical
                    ),
                    shape = RoundedCornerShape(20.dp)
                )
                .padding(2.dp),
            model = ImageRequest.Builder(LocalContext.current)
                .data(coverImage)
                .crossfade(true)
                .build(),
            filterQuality = FilterQuality.High,
            contentScale = ContentScale.FillBounds,
            contentDescription = stringResource(id = R.string.book_cover_image_content_description)
        )
    }
}

@Composable
fun MiddleContent(modifier: Modifier = Modifier, info: Info) = with(info) {

    Card(
        modifier = modifier,
        shape = RoundedCornerShape(20.dp),
        border = BorderStroke(2.dp, brush = DarkPurpleVertical)
    ) {

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(20.dp)
        )

        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(horizontal = 20.dp)
                .border(
                    BorderStroke(2.dp, brush = DarkPurpleVertical),
                    RoundedCornerShape(20.dp)
                )
                .clip(RoundedCornerShape(20.dp))
                .background(PurpleVertical, RoundedCornerShape(20.dp)),
            contentPadding = PaddingValues(vertical = 8.dp, horizontal = 16.dp),
            content = {
                genres?.let {
                    items(
                        it
                    ) { genre ->
                        Text(
                            text = genre,
                            fontSize = 12.sp,
                            color = Color.White,
                            fontFamily = rubikFamily,
                            fontWeight = FontWeight.Normal
                        )
                        if (genre != it.last()) {
                            Spacer(modifier = Modifier.width(4.dp))
                        }
                    }
                }
            })

        description?.let {
            val state = rememberScrollState()
            LaunchedEffect(Unit) { state.animateScrollTo(100) }
            Text(
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp).verticalScroll(state),
                text = it,
                fontSize = 14.sp,
                color = Color.Black,
                fontFamily = rubikFamily,
                fontWeight = FontWeight.Normal
            )
        }
    }
}

@Composable
fun BottomContent(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
    ) {
        Button(
            modifier = Modifier
                .fillMaxHeight()
                .width(0.dp)
                .weight(0.8f),
            contentPadding = PaddingValues(),
            shape = RoundedCornerShape(
                topStart = 20.dp,
                bottomStart = 20.dp,
                topEnd = 0.dp,
                bottomEnd = 0.dp
            ),
            border = BorderStroke(2.dp, brush = DarkPurpleVertical),
            colors = ButtonDefaults.buttonColors(contentColor = Color.Transparent),
            onClick = {}
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(WhiteVertical),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(R.string.button_chapters),
                    fontSize = 20.sp,
                    color = Color.Black,
                    fontFamily = rubikFamily,
                    fontWeight = FontWeight.ExtraBold
                )
            }
        }

        Button(
            modifier = Modifier
                .fillMaxHeight()
                .width(0.dp)
                .weight(1f),
            contentPadding = PaddingValues(),
            shape = RoundedCornerShape(
                topStart = 0.dp,
                bottomStart = 0.dp,
                topEnd = 20.dp,
                bottomEnd = 20.dp
            ),
            border = BorderStroke(2.dp, brush = DarkPurpleVertical),
            colors = ButtonDefaults.buttonColors(contentColor = Color.Transparent),
            onClick = {}
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(PurpleVertical),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(R.string.button_read),
                    fontSize = 20.sp,
                    color = Color.White,
                    fontFamily = rubikFamily,
                    fontWeight = FontWeight.ExtraBold
                )
            }
        }
    }
}

@Composable
fun Rating(modifier: Modifier = Modifier, rating: String) {
    Row(
        modifier = modifier,
    ) {

        Icon(
            imageVector = Icons.Sharp.StarRate, contentDescription = stringResource(
                id = R.string.rating_icon_content_description
            ),
            tint = Yellow,
            modifier = Modifier.size(20.dp)
        )

        Text(
            text = rating,
            fontSize = 14.sp,
            color = Color.White,
            fontFamily = rubikFamily,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
fun BackButton(modifier: Modifier = Modifier, onClick: () -> Unit = {}) {
    IconButton(modifier = modifier, onClick = onClick) {
        Icon(
            imageVector = Icons.Outlined.ArrowBack,
            contentDescription = stringResource(R.string.back_icon_content_description)
        )
    }
}

@Preview
@Composable
fun PreviewBackButton() {
    BackButton()
}

@Composable
fun BookmarkButton(modifier: Modifier = Modifier, onClick: () -> Unit = {}) {
    IconButton(modifier = modifier, onClick = onClick) {
        Icon(
            imageVector = Icons.Outlined.BookmarkAdd,
            contentDescription = stringResource(R.string.back_icon_content_description)
        )
    }
}

@Preview
@Composable
fun PreviewBookmarkButton() {
    BookmarkButton()
}