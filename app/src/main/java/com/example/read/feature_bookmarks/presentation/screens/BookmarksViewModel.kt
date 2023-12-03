package com.example.read.feature_bookmarks.presentation.screens

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.example.read.feature_bookmarks.domain.models.BookmarkType
import com.example.read.feature_bookmarks.domain.repositories.BookmarksRepository
import com.example.read.feature_home.domain.models.BookItem
import com.example.read.utils.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookmarksViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val bookmarksRepository: BookmarksRepository
) : BaseViewModel() {

    private val _bookmarksState = MutableStateFlow(PagingData.empty<BookItem>())
    val bookmarksState = _bookmarksState.asStateFlow()

    private val _bookmarkTypeState =
        savedStateHandle.getStateFlow<BookmarkType>(BOOKMARK_TYPE_KEY, BookmarkType.All)

    init {
        _bookmarkTypeState.flatMapLatest {
            bookmarksRepository.getBookmarks(it)
        }.collectFlowAsPaging(_bookmarksState)
    }

    fun setBookmarkType(type: BookmarkType) {
        when (type) {
            BookmarkType.READING -> savedStateHandle[BOOKMARK_TYPE_KEY] = type
            BookmarkType.READ -> savedStateHandle[BOOKMARK_TYPE_KEY] = type
            BookmarkType.IN_THE_PLANS -> savedStateHandle[BOOKMARK_TYPE_KEY] = type
            BookmarkType.ABANDONED -> savedStateHandle[BOOKMARK_TYPE_KEY] = type
            BookmarkType.FAVORITES -> savedStateHandle[BOOKMARK_TYPE_KEY] = type
            else -> savedStateHandle[BOOKMARK_TYPE_KEY] = type
        }
    }

    companion object {
        const val BOOKMARK_TYPE_KEY = "bookmark_type"
    }
}