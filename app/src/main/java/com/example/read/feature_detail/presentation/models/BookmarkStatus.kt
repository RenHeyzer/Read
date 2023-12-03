package com.example.read.feature_detail.presentation.models

import androidx.annotation.StringRes
import com.example.read.R

sealed class BookmarkStatus(@StringRes val message: Int) {

    data object Default: BookmarkStatus(R.string.add_book_to_bookmark_default)

    data object Success: BookmarkStatus(R.string.add_book_to_bookmark_success)

    data object AuthFailure: BookmarkStatus(R.string.add_book_to_bookmark_auth_failure)
}