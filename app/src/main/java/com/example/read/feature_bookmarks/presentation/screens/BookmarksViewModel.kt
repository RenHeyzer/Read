package com.example.read.feature_bookmarks.presentation.screens

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.example.read.feature_bookmarks.domain.models.BookmarkType
import com.example.read.feature_bookmarks.domain.repositories.BookmarksRepository
import com.example.read.feature_home.domain.models.BookItem
import com.example.read.feature_profile.domain.repositories.ProfileRepository
import com.example.read.utils.BookmarksRealtime
import com.example.read.utils.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookmarksViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val userRepository: ProfileRepository,
    private val bookmarksRepository: BookmarksRepository
) : BaseViewModel() {

    private val _bookmarksState = MutableStateFlow(PagingData.empty<BookItem>())
    val bookmarksState = _bookmarksState.asStateFlow()

    private val bookmarkTypeState =
        savedStateHandle.getStateFlow(BOOKMARK_TYPE_KEY, BookmarkType.All)

    private val _notAuthenticatedState = MutableStateFlow(false)
    val notAuthenticatedState = _notAuthenticatedState.asStateFlow()

    init {
        viewModelScope.launch {
            userRepository.getSessionStatus(
                authenticated = {
                    combine(bookmarkTypeState, BookmarksRealtime.channel) { type, change ->
                        type to change
                    }.flatMapLatest {
                        bookmarksRepository.getBookmarks(it.first)
                    }.collectFlowAsPaging(_bookmarksState)
                },
                notAuthenticated = {
                    _notAuthenticatedState.value = true
                }
            )
        }
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