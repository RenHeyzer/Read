package com.example.read.feature_home.presentation.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.read.R
import com.example.read.feature_detail.domain.models.Chapter
import com.example.read.feature_home.domain.models.BookItem
import com.example.read.ui.theme.DarkPurpleVertical
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.StandardTestDispatcher
import java.util.Date

@Composable
fun BooksContent(
    modifier: Modifier = Modifier,
    data: LazyPagingItems<BookItem>,
    listState: LazyListState = rememberLazyListState(),
    onItemClick: (path: String) -> Unit = {},
) {

    LazyColumn(
        modifier = modifier,
        state = listState,
        verticalArrangement = Arrangement.spacedBy(4.dp),
        contentPadding = PaddingValues(top = 4.dp, start = 4.dp, end = 4.dp)
    ) {
        items(data.itemCount) { index ->
            if (data[index] == null) return@items
            data[index]?.let {
                BookItem(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .border(BorderStroke(2.dp, DarkPurpleVertical), RoundedCornerShape(10.dp))
                        .background(
                            color = Color.White,
                            shape = RoundedCornerShape(10.dp)
                        )
                        .clickable {
                            it.info?.let { path -> onItemClick("") }
                        },
                    item = it,
                )
            }
        }

        /*pagingLoadStateItem(
            loadState = data.loadState.append,
            keySuffix = "append",
            loading = {
                Loading()
            },
            error = {
                if (it.endOfPaginationReached) {
                    Error {
                        data.retry()
                    }
                }
            },
        )*/
    }
}

@Composable
fun Loading() {
    LinearProgressIndicator(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(vertical = 20.dp, horizontal = 80.dp),
        color = Color.Black,
        trackColor = Color.White,
    )
}

@Preview
@Composable
fun PreviewLoading() {
    Loading()
}

@Composable
fun Error(onClick: () -> Unit = {}) {
    Text(
        text = stringResource(R.string.error_message),
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        textAlign = TextAlign.Center
    )
    Icon(
        imageVector = Icons.Filled.Refresh,
        contentDescription = stringResource(R.string.retry_icon_content_description),
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(top = 6.dp, bottom = 6.dp)
            .clickable {
                onClick()
            }
    )
}

@Preview
@Composable
fun PreviewError() {
    Error()
}

@Preview
@Composable
fun PreviewItems() {
    val data = List(50) {
        BookItem(
            id = "d9Iu94Q2SSvnBQWf8IzF",
            title = "Пойдем в караоке!",
            coverImage = "https://cover.imglib.info/uploads/cover/karaoke-iko/cover/jQPCea6qyp1o_250x350.jpg",
            latestChapter = Chapter(
                tome = 1,
                number = 2.5,
                releaseDate = Date(1537287840000)
            ),
        )
    }
    val flow = MutableStateFlow(PagingData.from(data))
    CompositionLocalProvider(value = LocalInspectionMode provides true) {
        val pagingItems = flow.collectAsLazyPagingItems(StandardTestDispatcher())
        BooksContent(data = pagingItems)
    }
}