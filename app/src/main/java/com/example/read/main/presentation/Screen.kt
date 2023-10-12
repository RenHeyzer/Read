package com.example.read.main.presentation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import com.example.read.R

sealed class Screen(val route: String, @StringRes val resourceId: Int) {
    object Books : Screen("books", R.string.books)
    object Bookmarks : Screen("bookmarks", R.string.bookmarks)
    object Account : Screen("account", R.string.account)
}