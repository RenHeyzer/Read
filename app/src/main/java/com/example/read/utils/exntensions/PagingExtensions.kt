package com.example.read.utils.exntensions

import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.runtime.Composable
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems

@Composable
fun <T : Any> CollectAsLazyPagingItems(
    data: LazyPagingItems<T>,
    loadState: LoadState,
    loading: (@Composable () -> Unit)? = null,
    error: (@Composable (message: String) -> Unit)? = null,
    notLoading: (@Composable (data: LazyPagingItems<T>) -> Unit)? = null
) {
    when (loadState) {
        LoadState.Loading -> {
            loading?.let {
                it()
            }
        }

        is LoadState.Error -> {
            loadState.error.localizedMessage?.let { message ->
                error?.let {
                    it(message)
                }
            }
        }

        is LoadState.NotLoading -> {
            notLoading?.let {
                it(data)
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