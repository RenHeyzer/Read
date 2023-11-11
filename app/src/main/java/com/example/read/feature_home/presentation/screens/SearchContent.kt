package com.example.read.feature_home.presentation.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.read.R
import com.example.read.feature_detail.domain.models.Chapter
import com.example.read.feature_home.domain.models.BookItem
import com.example.read.ui.theme.DarkPurpleVertical
import com.example.read.ui.theme.PurpleVertical
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import java.util.Date

@Composable
fun Search(
    modifier: Modifier = Modifier,
    searchQuery: MutableState<String>,
    pagingHits: LazyPagingItems<BookItem>,
    listState: LazyListState = rememberLazyListState(),
    onSubmit: () -> Unit = {},
    onValueChange: () -> Unit = {},
    onItemClick: (path: String) -> Unit = {},
) {

    val coroutineScope = rememberCoroutineScope()

    Column(modifier) {
        SearchBox(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .clip(RoundedCornerShape(10.dp))
                .border(BorderStroke(2.dp, DarkPurpleVertical), RoundedCornerShape(10.dp))
                .background(brush = PurpleVertical),
            searchQuery = searchQuery,
            onValueChange = {
                coroutineScope.launch { listState.scrollToItem(0) }
                onValueChange()
            },
            onSubmit = {
                coroutineScope.launch { listState.scrollToItem(0) }
                onSubmit()
            }
        )
        if (searchQuery.value.isNotEmpty()) {
            BooksContent(
                modifier = Modifier
                    .wrapContentSize()
                    .background(Color.White),
                data = pagingHits,
                onItemClick = { path ->
                    onItemClick(path)
                }
            )
        }
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
                tome = 1,
                number = 2.5,
                releaseDate = Date(1537287840000)
            ),
        )
    }
    val flow = MutableStateFlow(PagingData.from(data))
    CompositionLocalProvider(value = LocalInspectionMode provides true) {
        val pagingItems = flow.collectAsLazyPagingItems(StandardTestDispatcher())
        Search(
            modifier = Modifier.padding(top = 10.dp, start = 4.dp, end = 4.dp),
            pagingHits = pagingItems,
            searchQuery = remember {
                mutableStateOf(String())
            }
        )
    }
}

@Composable
fun SearchBox(
    modifier: Modifier = Modifier,
    searchQuery: MutableState<String>,
    onValueChange: () -> Unit = {},
    onSubmit: () -> Unit = {}
) {

    TextField(
        modifier = modifier,
        value = searchQuery.value,
        onValueChange = {
            searchQuery.value = it
            onValueChange()
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Outlined.Search,
                contentDescription = stringResource(R.string.search_icon_content_description),
                tint = Color.White,
            )
        },
        trailingIcon = {
            if (searchQuery.value.isNotEmpty()) {
                IconButton(
                    onClick = {
                        searchQuery.value = String()
                        onSubmit()
                    }
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Clear,
                        contentDescription = stringResource(R.string.clear_icon_content_description),
                        tint = Color.White,
                    )
                }
            }
        },
        placeholder = {
            Text(text = "Поиск...", color = Color.LightGray)
        },
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = Color.Transparent,
            focusedContainerColor = Color.Transparent,
            disabledContainerColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            focusedTextColor = Color.White,
            unfocusedTextColor = Color.White,
            disabledTextColor = Color.White
        ),
        singleLine = true,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions(
            onSearch = { onSubmit() }
        )
    )
}

@Preview
@Composable
fun PreviewSearchBox() {
    SearchBox(searchQuery = remember {
        mutableStateOf(String())
    })
}