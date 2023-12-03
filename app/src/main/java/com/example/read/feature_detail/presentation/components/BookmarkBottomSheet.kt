package com.example.read.feature_detail.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.read.feature_bookmarks.domain.models.BookmarkType
import com.example.read.ui.theme.Rubik

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookmarkBottomSheet(modifier: Modifier, onDismissRequest: () -> Unit, sheetState: SheetState, onSelect: (BookmarkType) -> Unit) {

    val bookmarkItems = listOf(
        BookmarkType.All,
        BookmarkType.READING,
        BookmarkType.READ,
        BookmarkType.IN_THE_PLANS,
        BookmarkType.ABANDONED,
        BookmarkType.FAVORITES,
    )

    var selected by remember {
        mutableStateOf(false)
    }

    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        sheetState = sheetState
    ) {
        bookmarkItems.forEach {
            FilterChip(
                modifier = Modifier.fillMaxWidth(),
                selected = selected,
                onClick = {
                    selected = !selected
                    onSelect(it)
                },
                label = {
                    Text(
                        text = it.type,
                        fontSize = 12.sp,
                        fontFamily = Rubik,
                        fontWeight = FontWeight.Normal
                    )
                },
                leadingIcon = if (selected) {
                    {
                        Icon(
                            imageVector = Icons.Filled.Done,
                            contentDescription = "Done icon",
                            modifier = Modifier.size(FilterChipDefaults.IconSize)
                        )
                    }
                } else {
                    null
                }
            )
        }
    }
}