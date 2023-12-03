package com.example.read.feature_home.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.read.R
import com.example.read.feature_detail.domain.models.Chapter
import com.example.read.feature_home.domain.models.BookItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.datetime.Instant

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Search(
    modifier: Modifier = Modifier,
    pagingHits: LazyPagingItems<BookItem>,
    listState: LazyListState = rememberLazyListState(),
    isSearchBarActive: Boolean,
    onActiveChange: (Boolean) -> Unit = {},
    onQueryChange: (query: String) -> Unit = {},
    onSearch: (query: String) -> Unit = {},
    onItemClick: (path: String) -> Unit = {},
) {
    var searchQuery by rememberSaveable {
        mutableStateOf(String())
    }
    val coroutineScope = rememberCoroutineScope()

    SearchBar(
        modifier = modifier,
        query = searchQuery,
        onQueryChange = {
            searchQuery = it
            coroutineScope.launch { listState.scrollToItem(0) }
            onQueryChange(searchQuery)
        },
        active = isSearchBarActive,
        onActiveChange = {
            onActiveChange(it)
        },
        onSearch = {
            coroutineScope.launch { listState.scrollToItem(0) }
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Outlined.Search,
                contentDescription = stringResource(R.string.search_icon_content_description),
                tint = Color.White,
            )
        },
        trailingIcon = {
            if (searchQuery.isNotEmpty()) {
                IconButton(onClick = {
                    searchQuery = String()
                    onSearch(searchQuery)
                }) {
                    Icon(
                        imageVector = Icons.Outlined.Clear,
                        contentDescription = stringResource(R.string.clear_icon_content_description),
                        tint = Color.White,
                    )
                }
            }
        },
        placeholder = {
            Text(text = stringResource(R.string.search_text_field_text), color = Color.LightGray)
        },
        colors = SearchBarDefaults.colors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            inputFieldColors = TextFieldDefaults.colors(
                unfocusedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                focusedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                disabledContainerColor = MaterialTheme.colorScheme.primaryContainer,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                disabledTextColor = Color.White,
                cursorColor = Color.White,
            )
        ),
        shape = RoundedCornerShape(10.dp)
    ) {
        BooksContent(
            modifier = Modifier.fillMaxWidth().weight(1f).background(Color.White),
            data = pagingHits,
            onItemClick = { path ->
                searchQuery = String()
                onSearch(searchQuery)
                onActiveChange(false)
                onItemClick(path)
            })
    }
}

@Preview
@Composable
fun PreviewSearch() {
    val data = List(50) {
        BookItem(
            id = "d9Iu94Q2SSvnBQWf8IzF",
            title = "Пойдем в караоке!",
            coverImage = "https://cover.imglib.info/uploads/cover/karaoke-iko/cover/jQPCea6qyp1o_250x350.jpg",
            latestChapter = Chapter(
                tome = 1, number = 2.5, releaseDate = Instant.DISTANT_PAST
            ),
        )
    }
    val flow = MutableStateFlow(PagingData.from(data))
    CompositionLocalProvider(value = LocalInspectionMode provides true) {
        val pagingItems = flow.collectAsLazyPagingItems(StandardTestDispatcher())
        Search(
            modifier = Modifier.padding(top = 10.dp, start = 4.dp, end = 4.dp),
            pagingHits = pagingItems,
            isSearchBarActive = true,
        )
    }
}