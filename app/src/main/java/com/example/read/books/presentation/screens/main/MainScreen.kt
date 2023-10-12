package com.example.read.books.presentation.screens.main

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import com.example.read.R
import com.example.read.books.domain.models.Item
import com.example.read.utils.exntensions.CollectAsLazyPagingItems
import com.example.read.utils.exntensions.pagingLoadStateItem
import java.util.Date

@Composable
fun MainScreen(
    navController: NavController,
    viewModel: BookViewModel = hiltViewModel()
) {

    val data = viewModel.booksState.collectAsLazyPagingItems()

    CollectAsLazyPagingItems(
        data = data,
        loadState = data.loadState.refresh,
        loading = {
            Bootstrap()
        },
        error = {

        },
        notLoading = {
            Items(data = it, getTimeSinceChapterRelease = { lastChapterReleaseDate ->
                viewModel.getTimeSinceChapterRelease(lastChapterReleaseDate)
            })
        })

}

@Composable
fun Bootstrap() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator(color = Color.Black)
    }
}

@Composable
fun Items(
    data: LazyPagingItems<Item>,
    getTimeSinceChapterRelease: (lastChapterReleaseData: Date?) -> String
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        items(data.itemCount) { index ->
            data[index]?.let {
                ItemBook(item = it, getTimeSinceChapterRelease = {
                    getTimeSinceChapterRelease(it.latestChapterReleaseDate)
                })
            }
        }

        pagingLoadStateItem(
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
        )
    }
}

@Composable
fun ItemBook(item: Item, getTimeSinceChapterRelease: () -> String) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(horizontal = 20.dp, vertical = 10.dp)
    ) {
        val roundedShape = RoundedCornerShape(corner = CornerSize(20.dp))
        AsyncImage(
            model = item.posterImage,
            contentDescription = stringResource(R.string.book_poster_image_content_description),
            modifier = Modifier
                .width(100.dp)
                .height(140.dp)
                .clip(roundedShape),
            contentScale = ContentScale.FillBounds,
        )

        Spacer(
            modifier = Modifier
                .width(20.dp)
                .wrapContentHeight()
        )

        Column {
            Text(
                text = item.title ?: "",
                color = Color.Black,
                fontSize = 20.sp,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(4.dp)
            )
            if (item.latestChapterTitle != null) {
                Text(
                    text = stringResource(
                        R.string.book_chapter_number_and_name_text,
                        item.latestChapterNumber ?: "",
                        item.latestChapterTitle
                    ),
                    color = Color.Black,
                    fontSize = 16.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            } else {
                Text(
                    text = stringResource(
                        R.string.book_chapter_number_and_name_text,
                        item.latestChapterNumber ?: "",
                        ""
                    ).replace("-", ""),
                    color = Color.Black,
                    fontSize = 16.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(4.dp)
            )
            Text(
                text = getTimeSinceChapterRelease(), color = Color.Gray, fontSize = 16.sp
            )
        }
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

@Composable
fun Error(onClick: () -> Unit) {
    Text(
        text = stringResource(R.string.error_message),
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        textAlign = TextAlign.Center
    )
    Icon(
        imageVector = Icons.Filled.Refresh,
        contentDescription = null,
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(top = 6.dp, bottom = 6.dp)
            .clickable {
                onClick()
            }
    )
}