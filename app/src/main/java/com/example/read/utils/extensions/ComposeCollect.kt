package com.example.read.utils.extensions

import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.example.read.utils.state_holders.UiState

@Composable
fun <T : Any, S> S.ConfigureAsLazyPagingItemsState(
    pagingItems: LazyPagingItems<T>,
    loadState: LoadState,
    loading: (@Composable S.() -> Unit)? = null,
    error: (@Composable S.(message: String) -> Unit)? = null,
    notLoading: (@Composable S.(data: LazyPagingItems<T>) -> Unit)? = null
) {
    when (loadState) {
        LoadState.Loading -> {
            loading?.let {
                it(this)
            }
        }

        is LoadState.Error -> {
            loadState.error.localizedMessage?.let { message ->
                error?.let {
                    it(this, message)
                }
            }
        }

        is LoadState.NotLoading -> {
            notLoading?.let {
                it(this, pagingItems)
            }
        }
    }
}

fun LazyListScope.pagingLoadStateItem(
    loadState: LoadState,
    keySuffix: String? = null,
    loading: (@Composable LazyItemScope.() -> Unit)? = null,
    error: (@Composable LazyItemScope.(LoadState.Error) -> Unit)? = null,
) {
    if (loading != null && loadState == LoadState.Loading) {
        item(
            key = keySuffix?.let { "loadingItem_$it" },
            content = loading,
        )
    }
    if (error != null && loadState is LoadState.Error) {
        item(
            key = keySuffix?.let { "errorItem_$it" },
            content = { error(loadState) },
        )
    }
}

@Composable
fun <T, S> S.ConfigureAsUiState(
    state: UiState<T>,
    loading: (@Composable S.() -> Unit)? = null,
    error: (@Composable S.(message: String) -> Unit)? = null,
    success: (@Composable S.(data: T) -> Unit)? = null
) {
    when (state) {
        is UiState.Loading -> loading?.let { it(this) }
        is UiState.Error -> {
            state.message?.let { message ->
                error?.let { it(this, message) }
            }
        }
        is UiState.Success -> {
            state.data?.let { data ->
                success?.let { it(this, data) }
            }
        }
    }
}