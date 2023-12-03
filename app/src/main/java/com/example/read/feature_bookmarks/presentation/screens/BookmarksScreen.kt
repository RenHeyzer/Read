package com.example.read.feature_bookmarks.presentation.screens

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.read.feature_bookmarks.domain.models.BookmarkType
import com.example.read.feature_home.presentation.components.BooksContent
import com.example.read.ui.theme.ReadTheme
import com.example.read.ui.theme.Rubik
import com.example.read.utils.extensions.ConfigureAsLazyPagingItemsState
import com.example.read.utils.extensions.LoadingIndicator

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookmarksScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    viewModel: BookmarksViewModel = hiltViewModel()
) {
    var isDropDownMenuExpanded by remember {
        mutableStateOf(false)
    }
    val dropDownItems = listOf(
        BookmarkType.All,
        BookmarkType.READING,
        BookmarkType.READ,
        BookmarkType.IN_THE_PLANS,
        BookmarkType.ABANDONED,
        BookmarkType.FAVORITES,
    )
    var selectedItemIndex by rememberSaveable {
        mutableIntStateOf(0)
    }

    val bookmarksPagingItems = viewModel.bookmarksState.collectAsLazyPagingItems()

    Column(modifier) {
        Card(
            modifier = Modifier
                .padding(top = 10.dp, end = 10.dp)
                .size(120.dp, 30.dp)
                .align(Alignment.End),
            shape = RoundedCornerShape(10.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
            onClick = { isDropDownMenuExpanded = true }
        ) {
            Box(modifier = Modifier.size(124.dp, 30.dp), contentAlignment = Alignment.Center) {
                Text(
                    text = dropDownItems[selectedItemIndex].type,
                    color = Color.White,
                    fontSize = 14.sp,
                    fontFamily = Rubik,
                    fontWeight = FontWeight.Normal,
                )
            }
            DropdownMenu(
                expanded = isDropDownMenuExpanded,
                onDismissRequest = { isDropDownMenuExpanded = false },
                offset = DpOffset(4.dp, 0.dp)
            ) {
                dropDownItems.forEachIndexed { index, value ->
                    DropdownMenuItem(
                        onClick = {
                            selectedItemIndex = index
                            viewModel.setBookmarkType(value)
                            isDropDownMenuExpanded = false
                        }
                    ) {
                        Text(
                            text = value.type,
                            fontSize = 12.sp,
                            fontFamily = Rubik,
                            fontWeight = FontWeight.Normal
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        ConfigureAsLazyPagingItemsState(
            pagingItems = bookmarksPagingItems,
            loadState = bookmarksPagingItems.loadState.refresh,
            loading = {
                LoadingIndicator()
            },
            error = {
                Log.e("error", it)
            },
            notLoading = {
                BooksContent(
                    modifier = Modifier.fillMaxSize(),
                    data = bookmarksPagingItems,
                )
            })
    }
}

@Preview
@Composable
fun PreviewBookmarksScreen() {
    ReadTheme {
        BookmarksScreen(navController = rememberNavController())
    }
}