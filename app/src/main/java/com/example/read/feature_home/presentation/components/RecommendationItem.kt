package com.example.read.feature_home.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.read.R
import com.example.read.feature_home.domain.models.RecommendationItem
import com.example.read.ui.theme.Purple60
import com.example.read.ui.theme.PurpleVerticalToBottom
import com.example.read.ui.theme.Rubik

@Composable
fun RecommendationItem(modifier: Modifier = Modifier, item: RecommendationItem) = with(item) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(10.dp),
        border = BorderStroke(2.dp, brush = PurpleVerticalToBottom)
    ) {
        AsyncImage(
            modifier = Modifier
                .width(104.dp)
                .height(142.dp)
                .clip(RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp))
                .padding(top = 2.dp, start = 2.dp, end = 2.dp),
            model = ImageRequest.Builder(LocalContext.current)
                .data(coverImage)
                .crossfade(true)
                .build(),
            contentDescription = stringResource(id = R.string.recommendation_cover_image_content_description),
            filterQuality = FilterQuality.High,
            contentScale = ContentScale.FillBounds,
        )
        Box(
            modifier = Modifier
                .width(104.dp)
                .height(40.dp)
                .background(PurpleVerticalToBottom)
                .border(
                    border = BorderStroke(2.dp, Purple60),
                    shape = RoundedCornerShape(bottomStart = 10.dp, bottomEnd = 10.dp)
                )
                .padding(vertical = 4.dp, horizontal = 10.dp),
            contentAlignment = Alignment.Center
        ) {
            title?.let { title ->
                Text(
                    text = title,
                    color = Color.White,
                    fontSize = 10.sp,
                    fontFamily = Rubik,
                    fontWeight = FontWeight.Medium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    lineHeight = 1.2.em
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewRecommendationItem() {
    RecommendationItem(
        modifier = Modifier.wrapContentSize(),
        item = RecommendationItem(
            id = "d9Iu94Q2SSvnBQWf8IzF",
            title = "Пойдем в караоке!",
            coverImage = "https://cover.imglib.info/uploads/cover/karaoke-iko/cover/jQPCea6qyp1o_250x350.jpg",
            posterImage = "https://cover.imglib.info/uploads/cover/karaoke-iko/cover/jQPCea6qyp1o_250x350.jpg",
        )
    )
}