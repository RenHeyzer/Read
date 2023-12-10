package com.example.read.feature_home.presentation.components

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.example.read.R
import com.example.read.feature_home.domain.models.RecommendationItem
import com.example.read.ui.theme.Purple60
import com.example.read.ui.theme.PurpleVerticalToBottom
import com.example.read.ui.theme.Rubik
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.StandardTestDispatcher
import java.util.UUID

@Composable
fun Recommendations(
    modifier: Modifier = Modifier,
    data: LazyPagingItems<RecommendationItem>
) {
    Column(modifier) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(horizontal = 4.dp),
            shape = RoundedCornerShape(10.dp),
            border = BorderStroke(2.dp, Purple60)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .background(PurpleVerticalToBottom)
                    .padding(vertical = 6.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(id = R.string.screen_home_recommendations_section),
                    color = Color.White,
                    fontSize = 16.sp,
                    fontFamily = Rubik,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(4.dp)
        )

        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            contentPadding = PaddingValues(horizontal = 4.dp)
        ) {
            items(
                count = data.itemCount,
                key = data.itemKey { it.id ?: UUID.randomUUID().toString() }
            ) { index ->
                if (data[index] == null) return@items
                data[index]?.let {
                    RecommendationItem(item = it)
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewRecommendations() {
    val data = List(50) {
        RecommendationItem(
            id = "d9Iu94Q2SSvnBQWf8IzF",
            title = "Пойдем в караоке!",
            coverImage = "https://cover.imglib.info/uploads/cover/karaoke-iko/cover/jQPCea6qyp1o_250x350.jpg",
            posterImage = "https://cover.imglib.info/uploads/cover/karaoke-iko/cover/jQPCea6qyp1o_250x350.jpg",
        )
    }
    val flow = MutableStateFlow(PagingData.from(data))
    CompositionLocalProvider(value = LocalInspectionMode provides true) {
        val pagingItems = flow.collectAsLazyPagingItems(StandardTestDispatcher())
        Recommendations(
            modifier = Modifier
                .padding(top = 4.dp)
                .fillMaxWidth()
                .wrapContentHeight(),
            data = pagingItems
        )
    }
}