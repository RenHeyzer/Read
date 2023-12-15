package com.example.read.feature_home.presentation.screens

import android.util.Log
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.example.read.feature_home.presentation.components.BookItem
import com.example.read.feature_home.presentation.components.PreviewItems
import com.example.read.feature_home.presentation.components.PreviewRecommendations
import com.example.read.feature_home.presentation.components.PreviewSearch
import com.example.read.feature_home.presentation.components.RecommendationSlides
import com.example.read.feature_home.presentation.components.Recommendations
import com.example.read.feature_home.presentation.models.LoadingType
import com.example.read.main.presentation.Screen
import com.example.read.ui.theme.LightGray
import com.example.read.utils.extensions.ConfigureAsLazyPagingItemsState
import com.example.read.utils.extensions.ConfigureAsUiState
import com.example.read.utils.extensions.LoadingIndicator
import com.example.read.utils.state_holders.UiState
import java.util.UUID

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    viewModel: HomeViewModel = hiltViewModel(),
    isSearchBarActive: Boolean
) {

    val bookPagingItems = viewModel.booksState.collectAsLazyPagingItems()
    val recommendationPagingItems = viewModel.recommendationsState.collectAsLazyPagingItems()
    val slidesState by viewModel.slidesState.collectAsState()
    val loadingState by viewModel.loadingState.collectAsState()
    val listState = rememberLazyListState()

    val firstVisibleItemScrollOffset by remember { derivedStateOf { listState.firstVisibleItemScrollOffset } }

    val minHeight = 0.dp
    val maxHeight = 480.dp

    val height by animateDpAsState(
        label = "collapsed height",
        targetValue = if (listState.canScrollBackward) minHeight else maxHeight,
        animationSpec = tween(durationMillis = 600)
    )

    val alpha by animateFloatAsState(
        label = "collapsed alpha",
        targetValue = if (listState.canScrollBackward) 0f else 1f,
        animationSpec = tween(durationMillis = 600)
    )

    Log.e("offset", firstVisibleItemScrollOffset.toString())

    ConfigureAsLazyPagingItemsState(
        pagingItems = bookPagingItems,
        loadState = bookPagingItems.loadState.refresh,
        error = {
            Log.e("error", it)
            viewModel.updateBooksLoadingState(LoadingType.Books(false))
        },
        notLoading = {
            viewModel.updateBooksLoadingState(LoadingType.Books(false))
        })

    ConfigureAsLazyPagingItemsState(
        pagingItems = recommendationPagingItems,
        loadState = recommendationPagingItems.loadState.refresh,
        error = { message ->
            Log.e("recommendations_error", message)
            viewModel.updateBooksLoadingState(LoadingType.Recommendations(false))
        },
        notLoading = {
            viewModel.updateBooksLoadingState(LoadingType.Recommendations(false))
        })

    ConfigureAsUiState(
        state = slidesState,
        error = { message ->
            Log.e("slides_error", message)
            viewModel.updateBooksLoadingState(LoadingType.Slides(false))
        },
        success = {
            viewModel.updateBooksLoadingState(LoadingType.Slides(false))
        }
    )

    with(loadingState) {
        if (!booksLoading && !recommendationsLoading && !slidesLoading) {
            LazyColumn(
                modifier = modifier,
                state = listState,
                verticalArrangement = Arrangement.spacedBy(4.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                contentPadding = PaddingValues(vertical = 10.dp)
            ) {
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(if(bookPagingItems.itemCount > 5) height else maxHeight)
                            .alpha(if(bookPagingItems.itemCount > 5) alpha else 1f)
                    ) {
                        if (slidesState is UiState.Success) {
                            (slidesState as UiState.Success).apply {
                                if (!data.isNullOrEmpty()) {
                                    RecommendationSlides(
                                        modifier = Modifier
                                            .padding(top = 4.dp)
                                            .fillMaxWidth()
                                            .background(Color.Transparent),
                                        slides = data
                                    )
                                }
                            }
                        }
                        Recommendations(
                            modifier = Modifier
                                .padding(top = 10.dp)
                                .fillMaxWidth()
                                .wrapContentHeight(),
                            data = recommendationPagingItems
                        )

                        Spacer(modifier = Modifier.height(6.dp))
                    }
                }
                items(
                    count = bookPagingItems.itemCount,
                    key = bookPagingItems.itemKey { it.id ?: UUID.randomUUID().toString() }
                ) { index ->
                    if (bookPagingItems[index] == null) return@items
                    bookPagingItems[index]?.let {
                        BookItem(
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentHeight()
                                .padding(top = 4.dp, start = 4.dp, end = 4.dp)
                                .background(Color.Transparent)
                                .clickable {
                                    it.info?.let { id ->
                                        navController.navigate(Screen.Detail.putIdArgument(id))
                                    }
                                },
                            item = it,
                        )
                    }
                }
            }
        }
        if (booksLoading || recommendationsLoading || slidesLoading) {
            LoadingIndicator()
        }
    }
}

@Preview
@Composable
fun PreviewMainScreen() {
    Column(modifier = Modifier.background(LightGray)) {
        PreviewSearch()
        PreviewRecommendations()
        PreviewItems()
    }
}