package com.example.read.feature_home.presentation.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.read.R
import com.example.read.feature_home.domain.models.BookItem
import com.example.read.ui.theme.DarkPurpleVertical
import com.example.read.ui.theme.rubikFamily
import java.util.Date

@Composable
fun BookItem(
    modifier: Modifier = Modifier,
    item: BookItem,
) = with(item) {

    Row(
        modifier = modifier
    ) {

        AsyncImage(
            modifier = Modifier
                .width(100.dp)
                .height(140.dp)
                .clip(RoundedCornerShape(topStart = 10.dp, bottomStart = 10.dp)),
            model = ImageRequest.Builder(LocalContext.current)
                .data(coverImage)
                .crossfade(true)
                .build(),
            filterQuality = FilterQuality.High,
            contentDescription = stringResource(R.string.book_cover_image_content_description),
            contentScale = ContentScale.FillBounds,
        )

        Spacer(
            modifier = Modifier
                .width(10.dp)
                .wrapContentHeight()
        )

        Column(modifier = Modifier.padding(top = 6.dp)) {
            Text(
                text = title ?: "",
                color = Color.Black,
                fontSize = 20.sp,
                fontFamily = rubikFamily,
                fontWeight = FontWeight.Medium,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(4.dp)
            )
            /*if (latestChapter?.title != null) {
                latestChapter.number?.let { number ->
                    Text(
                        text = stringResource(
                            R.string.book_chapter_number_and_name_text,
                            number,
                            latestChapter.title
                        ),
                        color = Color.Black,
                        fontSize = 16.sp,
                        fontFamily = rubikFamily,
                        fontWeight = FontWeight.Normal,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            } else {
                latestChapter?.number?.let { number ->
                    Text(
                        text = stringResource(
                            R.string.book_chapter_number_text,
                            number,
                        ),
                        color = Color.DarkGray,
                        fontSize = 16.sp,
                        fontFamily = rubikFamily,
                        fontWeight = FontWeight.Normal,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(4.dp)
            )
            latestChapter?.timeSinceRelease?.let {
                Text(
                    text = it, fontFamily = rubikFamily,
                    fontWeight = FontWeight.Normal, color = Color.Gray, fontSize = 14.sp
                )
            }*/
        }
    }
}

@Preview
@Composable
fun PreviewBookItem() {
    BookItem(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(top = 4.dp)
            .border(BorderStroke(2.dp, DarkPurpleVertical), RoundedCornerShape(10.dp))
            .background(
                color = Color.White,
                shape = RoundedCornerShape(10.dp)
            ),
        item = BookItem(
            id = "d9Iu94Q2SSvnBQWf8IzF",
            title = "Пойдем в караоке!",
            coverImage = "https://cover.imglib.info/uploads/cover/karaoke-iko/cover/jQPCea6qyp1o_250x350.jpg",
            /*latestChapter = Chapter(
                tome = 1,
                number = 2.5,
                releaseDate = Date(1537287840000)
            ),*/
        )
    )
}