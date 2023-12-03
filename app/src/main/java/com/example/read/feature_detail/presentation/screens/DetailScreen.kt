package com.example.read.feature_detail.presentation.screens

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.BookmarkAdd
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.read.R
import com.example.read.feature_bookmarks.domain.models.Bookmark
import com.example.read.feature_bookmarks.domain.models.BookmarkType
import com.example.read.feature_detail.presentation.components.BookmarkBottomSheet
import com.example.read.feature_detail.presentation.components.DetailContent
import com.example.read.feature_detail.presentation.models.BookmarkStatus
import com.example.read.utils.extensions.LoadingIndicator
import com.example.read.utils.extensions.ConfigureAsUiState
import io.github.jan.supabase.gotrue.SessionStatus
import kotlinx.coroutines.launch
import java.lang.IllegalStateException

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    viewModel: DetailViewModel = hiltViewModel(),
    snackbarHostState: SnackbarHostState
) {

    val infoState by viewModel.infoState.collectAsState()
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    var showBottomSheet by remember {
        mutableStateOf(false)
    }
    val bookmarkStatus by viewModel.bookmarkStatus.collectAsState()
    val sessionStatus by viewModel.sessionStatus.collectAsState()

    with(bookmarkStatus) {
        when (this) {
            is BookmarkStatus.AuthFailure -> {
                val message = stringResource(id = bookmarkStatus.message)
                LaunchedEffect(bookmarkStatus) {
                    snackbarHostState.showSnackbar(
                        message = message,
                        duration = SnackbarDuration.Short
                    )
                }
            }

            is BookmarkStatus.Success -> {
                val message = stringResource(id = bookmarkStatus.message)
                LaunchedEffect(bookmarkStatus) {
                    snackbarHostState.showSnackbar(
                        message = message,
                        duration = SnackbarDuration.Short
                    )
                }
            }

            else -> {}
        }
    }

    Column(modifier) {
        ConfigureAsUiState(
            state = infoState,
            loading = {
                LoadingIndicator()
            },
            error = { message ->
                Log.e("error", message)
            },
            success = { info ->
                DetailContent(
                    info = info,
                    onBackPressed = {
                        navController.navigateUp()
                    },
                    onBookmarkClick = {
                        showBottomSheet = true
                    }
                )

                if (showBottomSheet) {
                    BookmarkBottomSheet(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp),
                        onDismissRequest = { showBottomSheet = false },
                        sheetState = sheetState,
                        onSelect = {
                            when (sessionStatus) {
                                true -> {
                                    info.id?.let { id ->
                                        val bookmark = Bookmark(id, it.type)
                                        when (it) {
                                            BookmarkType.READING -> {
                                                viewModel.addBookToBookmark(bookmark = bookmark) {
                                                    viewModel.changeBookmarkStatus(BookmarkStatus.Success)
                                                }
                                            }

                                            BookmarkType.READ -> {
                                                viewModel.addBookToBookmark(bookmark = bookmark) {
                                                    viewModel.changeBookmarkStatus(BookmarkStatus.Success)
                                                }
                                            }

                                            BookmarkType.IN_THE_PLANS -> {
                                                viewModel.addBookToBookmark(bookmark = bookmark) {
                                                    viewModel.changeBookmarkStatus(BookmarkStatus.Success)
                                                }
                                            }

                                            BookmarkType.ABANDONED -> {
                                                viewModel.addBookToBookmark(bookmark = bookmark) {
                                                    viewModel.changeBookmarkStatus(BookmarkStatus.Success)
                                                }
                                            }

                                            BookmarkType.FAVORITES -> {
                                                viewModel.addBookToBookmark(bookmark = bookmark) {
                                                    viewModel.changeBookmarkStatus(BookmarkStatus.Success)
                                                }
                                            }

                                            else -> {
                                                viewModel.changeBookmarkStatus(BookmarkStatus.AuthFailure)
                                            }
                                        }
                                    }
                                }

                                false -> {
                                    viewModel.changeBookmarkStatus(BookmarkStatus.AuthFailure)
                                }
                            }
                            scope.launch { sheetState.hide() }.invokeOnCompletion {
                                if (!sheetState.isVisible) {
                                    showBottomSheet = false
                                }
                            }
                        }
                    )
                }
            }
        )
    }
}

@Composable
fun BackButton(modifier: Modifier = Modifier, onClick: () -> Unit = {}) {
    IconButton(modifier = modifier, onClick = onClick) {
        Icon(
            imageVector = Icons.Outlined.ArrowBack,
            contentDescription = stringResource(R.string.back_icon_content_description)
        )
    }
}

@Preview
@Composable
fun PreviewBackButton() {
    BackButton()
}

@Composable
fun BookmarkButton(modifier: Modifier = Modifier, onClick: () -> Unit = {}) {
    IconButton(modifier = modifier, onClick = onClick) {
        Icon(
            imageVector = Icons.Outlined.BookmarkAdd,
            contentDescription = stringResource(R.string.back_icon_content_description)
        )
    }
}

@Preview
@Composable
fun PreviewBookmarkButton() {
    BookmarkButton()
}