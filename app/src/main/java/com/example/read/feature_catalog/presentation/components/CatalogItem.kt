package com.example.read.feature_catalog.presentation.components

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.StarRate
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.read.R
import com.example.read.feature_detail.domain.models.Chapter
import com.example.read.feature_detail.domain.models.Status
import com.example.read.feature_home.domain.models.BookItem
import com.example.read.ui.theme.PurpleHorizontal
import com.example.read.ui.theme.PurpleVerticalToBottom
import com.example.read.ui.theme.Rubik
import com.example.read.ui.theme.Transparent20
import com.example.read.ui.theme.Yellow
import kotlinx.datetime.Instant

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CatalogItem(modifier: Modifier = Modifier, item: BookItem, onClick: () -> Unit) = with(item) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(10.dp),
        border = BorderStroke(2.dp, brush = PurpleHorizontal),
        onClick = onClick
    ) {
        Box {
            AsyncImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(162.dp)
                    .clip(RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp))
                    .padding(top = 2.dp, start = 2.dp, end = 2.dp),
                model = ImageRequest.Builder(LocalContext.current)
                    .data(coverImage)
                    .crossfade(true)
                    .build(),
                contentDescription = stringResource(id = R.string.book_cover_image_content_description),
                filterQuality = FilterQuality.High,
                contentScale = ContentScale.FillBounds,
                onError = {
                    Log.e("catalog_cover_image", it.result.throwable.message.toString())
                }
            )
            rating?.let {
                Rating(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(top = 10.dp, end = 10.dp)
                        .background(Transparent20, RoundedCornerShape(10.dp)),
                    it = it
                )
            }
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .background(PurpleVerticalToBottom)
                .border(
                    border = BorderStroke(2.dp, PurpleHorizontal),
                    shape = RoundedCornerShape(bottomStart = 10.dp, bottomEnd = 10.dp)
                )
                .padding(vertical = 4.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            title?.let { title ->
                val maxLength = 17
                val wrappedTitle = if (title.length > maxLength) {
                    "${title.removeRange(maxLength, title.length)}\n${
                        title.removeRange(
                            0,
                            maxLength
                        )
                    }"
                } else {
                    title
                }
                Text(
                    text = wrappedTitle,
                    color = Color.White,
                    fontSize = 12.sp,
                    fontFamily = Rubik,
                    fontWeight = FontWeight.Medium,
                    textAlign = TextAlign.Center,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
            if (releaseYear != null && status != null) {
                Text(
                    text = "$releaseYear ${status.status}",
                    color = Color.LightGray,
                    fontSize = 10.sp,
                    fontFamily = Rubik,
                    fontWeight = FontWeight.Medium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
            latestChapter?.number?.let { number ->
                Text(
                    text = stringResource(id = R.string.book_chapter_number_text, number),
                    color = Color.LightGray,
                    fontSize = 10.sp,
                    fontFamily = Rubik,
                    fontWeight = FontWeight.Medium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@Composable
fun Rating(modifier: Modifier = Modifier, it: Double) {
    val rating = it.toString().removeSuffix(".0")
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        Icon(
            imageVector = Icons.Sharp.StarRate,
            contentDescription = stringResource(id = R.string.rating_icon_content_description),
            tint = Yellow,
            modifier = Modifier.size(12.dp)
        )
        Text(
            modifier = Modifier,
            text = rating,
            fontSize = 10.sp,
            color = Color.White,
            fontFamily = Rubik,
            fontWeight = FontWeight.Medium
        )
    }
}

@Preview
@Composable
fun PreviewCatalogItem() {
    CatalogItem(
        modifier = Modifier.wrapContentSize(),
        item = BookItem(
            id = "d9Iu94Q2SSvnBQWf8IzF",
            title = "Пойдем в караоке!",
            coverImage = "https://cover.imglib.info/uploads/cover/karaoke-iko/cover/jQPCea6qyp1o_250x350.jpg",
            releaseYear = 2023,
            status = Status.FINISHED,
            rating = 9.0,
            latestChapter = Chapter(
                tome = 1,
                number = 2.5,
                releaseDate = Instant.DISTANT_PAST
            ),
        )
    ) {}
}