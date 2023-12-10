package com.example.read.feature_catalog.presentation.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.read.feature_catalog.presentation.components.CatalogContent
import com.example.read.feature_catalog.presentation.components.CatalogSwitch
import com.example.read.feature_catalog.presentation.components.PreviewCatalogContent
import com.example.read.main.presentation.Screen
import com.example.read.ui.theme.LightGray
import com.example.read.utils.extensions.LoadingIndicator
import com.example.read.utils.extensions.ConfigureAsLazyPagingItemsState

@Composable
fun CatalogScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    viewModel: CatalogViewModel = hiltViewModel()
) {

    val comicsPagingItems = viewModel.comicsState.collectAsLazyPagingItems()
    val mangaPagingItems = viewModel.mangaState.collectAsLazyPagingItems()
    var checkedCatalog by remember {
        mutableStateOf(false)
    }

    Column(modifier) {
        CatalogSwitch(
            modifier = Modifier
                .padding(top = 10.dp, start = 4.dp, end = 4.dp)
                .fillMaxWidth()
                .height(40.dp),
            checkedCatalog = { checked ->
                checkedCatalog = checked
            },
            checked = checkedCatalog
        )
        if (!checkedCatalog) {
            ConfigureAsLazyPagingItemsState(
                pagingItems = comicsPagingItems,
                loadState = comicsPagingItems.loadState.refresh,
                error = {
                    Log.e("error", it)
                },
                loading = {
                    LoadingIndicator()
                },
                notLoading = {
                    CatalogContent(
                        modifier = Modifier
                            .padding(top = 6.dp)
                            .fillMaxSize(),
                        data = it
                    ) { id ->
                        navController.navigate(Screen.Detail.putIdArgument(id))
                    }
                }
            )
        } else {
            ConfigureAsLazyPagingItemsState(
                pagingItems = mangaPagingItems,
                loadState = mangaPagingItems.loadState.refresh,
                error = {
                    Log.e("error", it)
                },
                loading = {
                    LoadingIndicator()
                },
                notLoading = {
                    CatalogContent(
                        modifier = Modifier
                            .padding(top = 6.dp)
                            .fillMaxSize(),
                        data = it
                    ) { id ->
                        navController.navigate(Screen.Detail.putIdArgument(id))
                    }
                }
            )
        }
    }
}

@Preview
@Composable
fun PreviewCatalogScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(LightGray)
    ) {
        CatalogSwitch(
            modifier = Modifier
                .padding(top = 10.dp, start = 4.dp, end = 4.dp)
                .fillMaxWidth()
                .weight(0.1f)
        )
        PreviewCatalogContent(
            modifier = Modifier
                .padding(top = 6.dp)
                .fillMaxWidth()
                .weight(1.4f)
        )
    }
}
