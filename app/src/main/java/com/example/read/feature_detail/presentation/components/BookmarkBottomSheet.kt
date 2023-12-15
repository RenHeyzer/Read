package com.example.read.feature_detail.presentation.components

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.read.R
import com.example.read.feature_bookmarks.domain.models.Bookmark
import com.example.read.feature_bookmarks.domain.models.BookmarkType
import com.example.read.ui.theme.LightGray
import com.example.read.ui.theme.Purple60
import com.example.read.ui.theme.ReadTheme
import com.example.read.ui.theme.Rubik
import com.example.read.utils.state_holders.UiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookmarkBottomSheet(
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit,
    sheetState: SheetState,
    inBookmarksState: UiState<Bookmark>,
    onSelect: (BookmarkType) -> Unit,
    onDelete: (id: String) -> Unit
) {

    val bookmarkItems = listOf(
        BookmarkType.READING,
        BookmarkType.READ,
        BookmarkType.IN_THE_PLANS,
        BookmarkType.ABANDONED,
        BookmarkType.FAVORITES,
    )

    val selected = remember {
        mutableStateMapOf(
            BookmarkType.READING.name to false,
            BookmarkType.READ.name to false,
            BookmarkType.IN_THE_PLANS.name to false,
            BookmarkType.ABANDONED.name to false,
            BookmarkType.FAVORITES.name to false,
        )
    }

    ModalBottomSheet(
        modifier = modifier,
        onDismissRequest = onDismissRequest,
        sheetState = sheetState,
        shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
        containerColor = LightGray,
    ) {
        bookmarkItems.forEach { type ->
            FilterChip(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .align(Alignment.CenterHorizontally),
                border = FilterChipDefaults.filterChipBorder(borderColor = Purple60),
                colors = FilterChipDefaults.filterChipColors(
                    containerColor = if (selected[type.name] == true) Purple60 else Color.Transparent
                ),
                selected = if (selected[type.name] != null) selected[type.name]!! else false,
                onClick = {
                    selected[type.name] =
                        if (selected[type.name] != null) !selected[type.name]!! else false
                    onSelect(type)
                },
                label = {
                    Text(
                        text = type.type,
                        color = if (selected[type.name] == true) Color.White else Color.Black,
                        fontSize = 12.sp,
                        fontFamily = Rubik,
                        fontWeight = FontWeight.Normal
                    )
                },
                leadingIcon = if (selected[type.name] == true) {
                    {
                        Icon(
                            imageVector = Icons.Filled.Done,
                            contentDescription = stringResource(id = R.string.done_icon_content_description),
                            modifier = Modifier.size(FilterChipDefaults.IconSize),
                            tint = Color.White
                        )
                    }
                } else {
                    null
                }
            )
        }

        when (inBookmarksState) {
            is UiState.Error -> {
                inBookmarksState.message?.let { Log.e("bookmark", it) }
            }

            is UiState.Loading -> {}
            is UiState.Success -> {
                if (inBookmarksState.data != null) {
                    BookmarkType.entries.find {
                        it.type == inBookmarksState.data.type
                    }?.let {
                        selected[it.name] = true
                    }
                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp)
                            .align(Alignment.CenterHorizontally),
                        onClick = {
                            onDelete(inBookmarksState.data.id)
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                        border = BorderStroke(1.dp, Color.Red)
                    ) {
                        Text(
                            text = stringResource(R.string.delete_book_from_bookmarks),
                            color = Color.Red,
                            fontSize = 12.sp,
                            fontFamily = Rubik,
                            fontWeight = FontWeight.Normal
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun PreviewBookmarkBottomSheet() {
    ReadTheme {
        BookmarkBottomSheet(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp),
            onDismissRequest = { },
            sheetState = rememberModalBottomSheetState(),
            onSelect = { },
            onDelete = { },
            inBookmarksState = UiState.Loading()
        )
    }
}