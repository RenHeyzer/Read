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
import com.example.read.feature_detail.domain.usecases.AddBookToBookmarksUseCase
import com.example.read.feature_detail.domain.usecases.BookmarkResult
import com.example.read.feature_detail.domain.usecases.DeleteBookFromBookmarksUseCase
import com.example.read.feature_profile.domain.repositories.ProfileRepository
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
    private val profileRepository: ProfileRepository,
    private val bookmarksRepository: BookmarksRepository,
    private val addBookToBookmark: AddBookToBookmarksUseCase,
    private val deleteBookFromBookmarks: DeleteBookFromBookmarksUseCase
) : BaseViewModel() {

    private val _infoState = MutableStateFlow<UiState<Info>>(UiState.Loading())
    val infoState = _infoState.asStateFlow()

    private val _bookmarkState =
        MutableSharedFlow<BookmarkState>(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)
    val bookmarkState = _bookmarkState.asSharedFlow()

    private val _inBookmarksState = MutableStateFlow<UiState<Bookmark>>(UiState.Loading())
    val inBookmarksState = _inBookmarksState.asStateFlow()

    private val bookId: String = checkNotNull(savedStateHandle[INFO_ID_KEY])

    init {
        getBookInfo()
//        connectToBookmarksRealtime()
    }

    fun getBookInfo() {
        viewModelScope.launch {
            kotlin.runCatching {
                bookInfoRepository.getBookInfo(bookId)
            }.onSuccess {
                _infoState.value = UiState.Success(it)
                Log.e("getBookInfo", it.toString())
                checkBookInBookmarks()
            }.onFailure {
                it.message?.let { message ->
                    Log.e("getBookInfo", message)
                    _infoState.value = UiState.Error(message)
                }
            }
        }
    }

    fun checkBookInBookmarks() {
        viewModelScope.launch {
            profileRepository.getSessionStatus(
                authenticated = { session ->
                    try {
                        val userId = checkNotNull(session.user?.id)
                        val inBookmark =
                            bookmarksRepository.checkBookInBookmarks(
                                bookId = bookId,
                                userId = userId
                            )
                        _inBookmarksState.value = UiState.Success(inBookmark)
                    } catch (e: Exception) {
                        Log.e("bookmark", "checkBookInBookmarks: ${e.message}")
                        _inBookmarksState.value =
                            UiState.Error(e.message ?: "Unknown error!")
                    }
                }
            )
        }
    }

    fun addBookToBookmarks(bookId: String, type: BookmarkType) {
        when (type) {
            BookmarkType.READING -> {
                handleAddToBookmarksResult(bookId, type)
            }

            BookmarkType.READ -> {
                handleAddToBookmarksResult(bookId, type)
            }

            BookmarkType.IN_THE_PLANS -> {
                handleAddToBookmarksResult(bookId, type)
            }

            BookmarkType.ABANDONED -> {
                handleAddToBookmarksResult(bookId, type)
            }

            BookmarkType.FAVORITES -> {
                handleAddToBookmarksResult(bookId, type)
            }

            else -> {
                handleAddToBookmarksResult(bookId, type)
            }
        }
    }

    fun handleAddToBookmarksResult(bookId: String, type: BookmarkType) {
        viewModelScope.launch {
            when (val result = addBookToBookmark.invoke(bookId, type)) {
                is BookmarkResult.Success -> {
                    checkBookInBookmarks()
                    _bookmarkState.emit(BookmarkState.Success())
                }

                is BookmarkResult.AuthFailure -> _bookmarkState.emit(BookmarkState.AuthFailure())
                is BookmarkResult.Error -> {
                    Log.e("add_bookmark", result.exception.message ?: "Unknown error!")
                    _bookmarkState.emit(BookmarkState.Error())
                }

                is BookmarkResult.AlreadyExist -> BookmarkState.AlreadyExist()
            }
        }
    }

    fun deleteBookFromBookmarks(id: String) {
        viewModelScope.launch {
            when (val result = deleteBookFromBookmarks.invoke(id)) {
                is BookmarkResult.Success -> {
                    Log.e("delete_bookmark", "success")
                    checkBookInBookmarks()
                    _bookmarkState.emit(BookmarkState.Success(false))
                }

                is BookmarkResult.AuthFailure -> _bookmarkState.emit(BookmarkState.AuthFailure())
                is BookmarkResult.Error -> {
                    Log.e("delete_bookmark", result.exception.message ?: "Unknown error!")
                    _bookmarkState.emit(BookmarkState.Error())
                }

                else -> throw IllegalStateException("Unexpected result of deleteBookFromBookmarks")
            }
        }
    }

    fun connectToBookmarksRealtime() {
        viewModelScope.launch {
            kotlin.runCatching {
                bookmarksRepository.connectToBookmarksRealtime(
                    scope = this,
                    onDelete = {
                        _inBookmarksState.value = UiState.Success(null)
                    },
                    onInsert = {
                        _inBookmarksState.value = UiState.Success(it)
                    },
                    onSelect = {
                        _inBookmarksState.value = UiState.Success(it)
                    },
                    onUpdate = {
                        _inBookmarksState.value = UiState.Success(it)
                    }
                )
            }.onFailure {
                Log.e("delete_bookmark", it.message ?: "Unknown error!")
                _inBookmarksState.value = UiState.Error(it.message ?: "Unknown error!")
            }
        }
    }

    companion object {
        const val INFO_ID_KEY = "id"
    }
}

sealed class BookmarkState(@StringRes val message: Int) {

    data object Default : BookmarkState(R.string.add_book_to_bookmark_default)

    data class Success(val add: Boolean = true) :
        BookmarkState(
            if (add) R.string.add_book_to_bookmarks_success
            else R.string.delete_from_bookmarks_success
        )

    class AuthFailure : BookmarkState(R.string.bookmark_auth_failure)

    class Error : BookmarkState(R.string.error_message)

    class AlreadyExist : BookmarkState(R.string.bookmark_already_exist)
}