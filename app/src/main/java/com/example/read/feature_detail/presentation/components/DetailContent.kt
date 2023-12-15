package com.example.read.feature_detail.presentation.components

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.read.feature_bookmarks.domain.models.Bookmark
import com.example.read.feature_detail.domain.models.Info
import com.example.read.utils.state_holders.UiState

@Composable
fun ColumnScope.DetailContent(
    info: Info,
    onBackPressed: () -> Unit,
    onBookmarkClick: () -> Unit,
    inBookmarksState: UiState<Bookmark>
) {

    TopContent(
        modifier = Modifier
            .wrapContentWidth()
            .height(0.dp)
            .weight(1.4f),
        info = info,
        onBackPressed = onBackPressed,
        onBookmarkClick = onBookmarkClick,
        inBookmarksState = inBookmarksState
    )

    Spacer(
        modifier = Modifier
            .fillMaxWidth()
            .height(10.dp)
    )

    MiddleContent(
        modifier = Modifier
            .fillMaxWidth()
            .height(0.dp)
            .padding(horizontal = 4.dp)
            .weight(2.1f),
        info = info
    )

    Spacer(
        modifier = Modifier
            .fillMaxWidth()
            .height(10.dp)
    )

    BottomContent(
        modifier = Modifier
            .fillMaxWidth()
            .height(0.dp)
            .padding(horizontal = 4.dp)
            .weight(0.3f)
    )

    Spacer(
        modifier = Modifier
            .fillMaxWidth()
            .height(10.dp)
    )
}