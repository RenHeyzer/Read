package com.example.read.feature_detail.presentation.screens

import android.util.Log
import androidx.annotation.StringRes
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.read.R
import com.example.read.feature_bookmarks.domain.models.Bookmark
import com.example.read.feature_bookmarks.domain.models.BookmarkType
import com.example.read.feature_bookmarks.domain.repositories.BookmarksRepository
import com.example.read.feature_detail.domain.models.Info
import com.example.read.feature_detail.domain.repositories.BookInfoRepository
import com.example.read.feature_detail.domain.usecases.AddBookToBookmarkUseCase
import com.example.read.feature_detail.domain.usecases.BookmarkResult
import com.example.read.utils.base.BaseViewModel
import com.example.read.utils.state_holders.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val bookInfoRepository: BookInfoRepository,
    private val bookmarksRepository: BookmarksRepository,
    private val addBookToBookmark: AddBookToBookmarkUseCase
) : BaseViewModel() {

    private val _infoState = MutableStateFlow<UiState<Info>>(UiState.Loading())
    val infoState = _infoState.asStateFlow()

    private val _bookmarkStatus =
        MutableSharedFlow<BookmarkStatus>(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)
    val bookmarkStatus = _bookmarkStatus.asSharedFlow()

    private val _sessionStatus = MutableStateFlow(false)
    val sessionStatus = _sessionStatus.asStateFlow()

    private val id: String = checkNotNull(savedStateHandle[INFO_ID_KEY])

    init {
        getBookInfo()
    }

    fun getBookInfo() {
        viewModelScope.launch {
            kotlin.runCatching {
                bookInfoRepository.getBookInfo(id)
            }.onSuccess {
                _infoState.value = UiState.Success(it)
                Log.e("getBookInfo", it.toString())
            }.onFailure {
                it.message?.let { message ->
                    Log.e("getBookInfo", message)
                    _infoState.value = UiState.Error(message)
                }
            }
        }
    }

    fun addBookToBookmark(id: String, type: BookmarkType) {
        val bookmark = Bookmark(id, type.type)
        when (type) {
            BookmarkType.READING -> {
                handleAddToBookmarkResult(bookmark)
            }

            BookmarkType.READ -> {
                handleAddToBookmarkResult(bookmark)
            }

            BookmarkType.IN_THE_PLANS -> {
                handleAddToBookmarkResult(bookmark)
            }

            BookmarkType.ABANDONED -> {
                handleAddToBookmarkResult(bookmark)
            }

            BookmarkType.FAVORITES -> {
                handleAddToBookmarkResult(bookmark)
            }

            else -> {
                handleAddToBookmarkResult(bookmark)
            }
        }
    }

    fun handleAddToBookmarkResult(bookmark: Bookmark) {
        viewModelScope.launch {
            when (val result = addBookToBookmark.invoke(bookmark)) {
                BookmarkResult.Success -> _bookmarkStatus.emit(BookmarkStatus.Success)
                BookmarkResult.AuthFailure -> _bookmarkStatus.emit(BookmarkStatus.AuthFailure())
                is BookmarkResult.Error -> {
                    Log.e("add_bookmark", result.message)
                    _bookmarkStatus.emit(BookmarkStatus.Error)
                }

                BookmarkResult.Else -> _bookmarkStatus.emit(BookmarkStatus.Error)
            }
        }
    }

    companion object {
        const val INFO_ID_KEY = "id"
    }
}

sealed class BookmarkStatus(@StringRes val message: Int) {

    data object Default: BookmarkStatus(R.string.add_book_to_bookmark_default)

    data object Success : BookmarkStatus(R.string.add_book_to_bookmark_success)

    class AuthFailure : BookmarkStatus(R.string.bookmark_auth_failure)

    data object Error : BookmarkStatus(R.string.error_message)
}