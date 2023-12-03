package com.example.read.feature_home.presentation.screens

import android.util.Log
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateValueAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.read.feature_home.presentation.components.BooksContent
import com.example.read.feature_home.presentation.components.PreviewItems
import com.example.read.feature_home.presentation.components.PreviewRecommendations
import com.example.read.feature_home.presentation.components.PreviewSearch
import com.example.read.feature_home.presentation.components.RecommendationSlides
import com.example.read.feature_home.presentation.components.Recommendations
import com.example.read.feature_home.presentation.components.Search
import com.example.read.feature_home.presentation.models.LoadingType
import com.example.read.main.presentation.Screen
import com.example.read.ui.theme.White80
import com.example.read.utils.extensions.ConfigureAsLazyPagingItemsState
import com.example.read.utils.extensions.ConfigureAsUiState
import com.example.read.utils.extensions.LoadingIndicator
import kotlinx.coroutines.delay

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
    val listState = rememberLazyListState()

    val firstVisibleItemScrollOffset by remember { derivedStateOf { listState.firstVisibleItemScrollOffset } }

    val height by animateDpAsState(
        label = "collapsed height",
        targetValue = if (listState.canScrollBackward) 0.dp else 500.dp,
        animationSpec = tween(durationMillis = 300)
    )

    val alpha by animateFloatAsState(
        label = "collapsed alpha",
        targetValue = if (listState.canScrollBackward) 0f else 1f,
        animationSpec = tween(durationMillis = 300)
    )

    Log.e("offset", firstVisibleItemScrollOffset.toString())

    val loadingState by viewModel.loadingState.collectAsState()

    Box(modifier = modifier) {
        with(loadingState) {
            Column(modifier = Modifier.fillMaxSize()) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(height)
                        .alpha(alpha)
                ) {
                    ConfigureAsUiState(
                        state = slidesState,
                        loading = {
                            viewModel.updateBooksLoadingState(LoadingType.Slides(true))
                        },
                        error = { message ->
                            Log.e("slides_error", message)
                            viewModel.updateBooksLoadingState(LoadingType.Slides(false))
                        },
                        success = { slides ->
                            viewModel.updateBooksLoadingState(LoadingType.Slides(false))
                            if (!booksLoading && !recommendationsLoading && !slidesLoading) {
                                if (slides.isNotEmpty()) {
                                    RecommendationSlides(
                                        modifier = Modifier
                                            .padding(top = 4.dp)
                                            .fillMaxWidth()
                                            .background(Color.Transparent)
                                            .align(Alignment.CenterHorizontally),
                                        slides = slides
                                    )
                                }
                            }
                        }
                    )

                    ConfigureAsLazyPagingItemsState(
                        pagingItems = recommendationPagingItems,
                        loadState = recommendationPagingItems.loadState.refresh,
                        loading = {
                            viewModel.updateBooksLoadingState(LoadingType.Recommendations(true))
                        },
                        error = { message ->
                            Log.e("recommendations_error", message)
                            viewModel.updateBooksLoadingState(LoadingType.Recommendations(false))
                        },
                        notLoading = { data ->
                            viewModel.updateBooksLoadingState(LoadingType.Recommendations(false))
                            if (!booksLoading && !recommendationsLoading && !slidesLoading) {
                                Recommendations(
                                    modifier = Modifier
                                        .padding(top = 10.dp)
                                        .fillMaxWidth()
                                        .wrapContentHeight(),
                                    data = data
                                )
                            }
                        })

                    Spacer(modifier = Modifier.height(6.dp))
                }

                ConfigureAsLazyPagingItemsState(
                    pagingItems = bookPagingItems,
                    loadState = bookPagingItems.loadState.refresh,
                    loading = {
                        viewModel.updateBooksLoadingState(LoadingType.Books(true))
                    },
                    error = {
                        Log.e("error", it)
                        viewModel.updateBooksLoadingState(LoadingType.Books(false))
                    },
                    notLoading = {
                        viewModel.updateBooksLoadingState(LoadingType.Books(false))
                        if (!booksLoading && !recommendationsLoading && !slidesLoading) {
                            BooksContent(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                data = bookPagingItems,
                                listState = listState,
                                onItemClick = { id ->
                                    navController.navigate(Screen.Detail.putIdArgument(id))
                                }
                            )
                        }
                    })
            }
            if (booksLoading && recommendationsLoading && slidesLoading) {
                LoadingIndicator()
            }
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