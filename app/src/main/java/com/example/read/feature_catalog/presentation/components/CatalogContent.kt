package com.example.read.feature_catalog.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.read.feature_detail.domain.models.Chapter
import com.example.read.feature_detail.domain.models.Status
import com.example.read.feature_home.domain.models.BookItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.datetime.Instant

@Composable
fun CatalogContent(modifier: Modifier = Modifier, data: LazyPagingItems<BookItem>, onClick: (id: String) -> Unit) {
    LazyVerticalStaggeredGrid(
        modifier = modifier,
        columns = StaggeredGridCells.Fixed(3),
        verticalItemSpacing = 4.dp,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        contentPadding = PaddingValues(4.dp)
    ) {
        items(data.itemCount) { index ->
            if (data[index] == null) return@items
            data[index]?.let {
                CatalogItem(
                    modifier = Modifier.wrapContentSize(),
                    item = it,
                    onClick = {
                        it.id?.let { id -> onClick(id) }
                    }
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewCatalogContent(modifier: Modifier = Modifier) {
    val data = List(50) {
        BookItem(
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
    }
    val flow = MutableStateFlow(PagingData.from(data))
    CompositionLocalProvider(value = LocalInspectionMode provides true) {
        val pagingItems = flow.collectAsLazyPagingItems(StandardTestDispatcher())
        CatalogContent(modifier = modifier, data = pagingItems) {}
    }
}