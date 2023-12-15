package com.example.read.feature_detail.presentation.components

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.read.R
import com.example.read.feature_bookmarks.domain.models.Bookmark
import com.example.read.feature_detail.domain.models.Info
import com.example.read.feature_detail.presentation.screens.BackButton
import com.example.read.feature_detail.presentation.screens.BookmarkButton
import com.example.read.ui.theme.Purple60
import com.example.read.ui.theme.PurpleHorizontal
import com.example.read.ui.theme.PurpleVerticalToBottom
import com.example.read.ui.theme.Rubik
import com.example.read.utils.extensions.ConfigureAsUiState
import com.example.read.utils.state_holders.UiState

@Composable
fun TopContent(
    modifier: Modifier = Modifier,
    info: Info,
    onBackPressed: () -> Unit,
    onBookmarkClick: () -> Unit,
    inBookmarksState: UiState<Bookmark>
) = with(info) {

    Box(
        modifier = modifier
    ) {
        BackButton(
            Modifier
                .align(Alignment.TopStart), onClick = onBackPressed
        )

        when (inBookmarksState) {
            is UiState.Error -> {
                inBookmarksState.message?.let { Log.e("bookmark", it) }
            }

            is UiState.Loading -> {}
            is UiState.Success -> {
                BookmarkButton(
                    Modifier.align(Alignment.TopEnd),
                    onClick = onBookmarkClick,
                    inBookmarks = inBookmarksState.data == null
                )
            }
        }

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(top = 50.dp, start = 4.dp, end = 4.dp),
            shape = RoundedCornerShape(10.dp),
            border = BorderStroke(2.dp, brush = PurpleHorizontal)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(brush = PurpleVerticalToBottom),
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
                        fontFamily = Rubik,
                        fontWeight = FontWeight.Medium,
                        fontStyle = FontStyle.Normal,
                        fontSize = 18.sp,
                        color = Color.White,
                    )
                }

                if (releaseYear != null || type != null || status != null) {
                    Text(
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        text = "$releaseYear ${type?.type} ${status?.status}",
                        fontFamily = Rubik,
                        fontWeight = FontWeight.Medium,
                        fontStyle = FontStyle.Normal,
                        fontSize = 12.sp,
                        color = Color.LightGray,
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
                .padding(top = 5.dp)
                .width(124.dp)
                .height(184.dp)
                .align(Alignment.TopCenter)
                .clip(RoundedCornerShape(10.dp))
                .border(
                    BorderStroke(
                        width = 2.dp,
                        color = Purple60
                    ),
                    shape = RoundedCornerShape(10.dp)
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