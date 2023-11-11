package com.example.read.feature_home.presentation.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.read.main.presentation.Screen
import com.example.read.ui.theme.Purple90
import com.example.read.ui.theme.White80
import com.example.read.utils.extensions.ConfigureAsLazyPagingItemsState
import com.example.read.utils.extensions.calculateCurrentSize
import com.example.read.utils.extensions.getFraction

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel()
) {

    val bookPagingItems = viewModel.booksState.collectAsLazyPagingItems()
//    val recommendationPagingItems = viewModel.recommendationsState.collectAsLazyPagingItems()
//    val slidesStateItems = viewModel.slidesState.collectAsState()
    val listState = rememberLazyListState()
    val searchQuery = remember {
        mutableStateOf(String())
    }

    var maximumScroll: Float
    var topContentExpandedHeight: Float
    var topContentCollapsedHeight: Float

    with(LocalDensity.current) {
        maximumScroll = 50.dp.toPx()

        topContentExpandedHeight = 500.dp.toPx()
        topContentCollapsedHeight = 0.dp.toPx()
    }

    val currentOffset: Float by remember {
        derivedStateOf {
            val offset = listState.firstVisibleItemScrollOffset

            if (listState.firstVisibleItemIndex > 0) {
                maximumScroll
            } else {
                offset.toFloat().coerceIn(0f, maximumScroll)
            }
        }
    }

    var topContentHeight = 0.dp
    var topContentHeightPx = 0f
    var fraction = 0f

    with(LocalDensity.current) {
        fraction = getFraction(maximumScroll, currentOffset)
        topContentHeightPx =
            calculateCurrentSize(topContentCollapsedHeight, topContentExpandedHeight, fraction)
        topContentHeight = topContentHeightPx.toDp()
    }

    Column(modifier = modifier) {
        /*Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(topContentHeight)
        ) {
            Search(
                modifier = Modifier
                    .padding(top = 10.dp, start = 4.dp, end = 4.dp),
                pagingHits = bookPagingItems,
                searchQuery = searchQuery,
                onValueChange = { viewModel.setSearchQuery(searchQuery.value) },
                onSubmit = { viewModel.setSearchQuery(searchQuery.value) },
                onItemClick = { path ->
                    navController.navigate("${Screen.Detail.route}/${path.replace("/", "_")}")
                })

            ConfigureAsUiState(
                state = slidesStateItems,
                error = { message ->
                    Log.e("slides_error", message)
                },
                success = { slides ->
                    RecommendationSlides(
                        modifier = Modifier
                            .padding(top = 4.dp, start = 10.dp, end = 10.dp)
                            .fillMaxWidth()
                            .height(204.dp)
                            .align(Alignment.CenterHorizontally),
                        slides = slides
                    )
                }
            )

            Recommendations(
                modifier = Modifier
                    .padding(top = 10.dp)
                    .fillMaxWidth()
                    .wrapContentHeight(),
                recommendationPagingItems = recommendationPagingItems
            )
        }*/

        Box(modifier = Modifier.fillMaxSize()) {
            ConfigureAsLazyPagingItemsState(
                pagingItems = bookPagingItems,
                loadState = bookPagingItems.loadState.refresh,
                loading = {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center),
                        color = Purple90
                    )
                },
                error = {
                    Log.e("error", it)
                },
                notLoading = {
                    if (searchQuery.value.isEmpty()) {
                        BooksContent(
                            modifier = Modifier
                                .padding(top = 6.dp)
                                .background(White80),
                            data = bookPagingItems,
                            listState = listState,
                            onItemClick = { path ->
                                navController.navigate(
                                    "${Screen.Detail.route}/${
                                        path.replace(
                                            "/",
                                            "_"
                                        )
                                    }"
                                )
                            }
                        )
                    }
                })
        }
    }
}

@Preview
@Composable
fun PreviewMainScreen() {
    Column(modifier = Modifier.background(White80)) {
        PreviewSearch()
        PreviewRecommendations()
        PreviewItems()
    }
}