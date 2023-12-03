package com.example.read.feature_detail.presentation.screens

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.read.feature_bookmarks.domain.models.Bookmark
import com.example.read.feature_bookmarks.domain.repositories.BookmarksRepository
import com.example.read.feature_detail.domain.models.Info
import com.example.read.feature_detail.domain.repositories.BookInfoRepository
import com.example.read.feature_detail.presentation.models.BookmarkStatus
import com.example.read.feature_profile.domain.repositories.UserRepository
import com.example.read.utils.state_holders.UiState
import com.example.read.utils.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val bookInfoRepository: BookInfoRepository,
    private val bookmarksRepository: BookmarksRepository,
    private val userRepository: UserRepository,
) : BaseViewModel() {

    private val _infoState = MutableStateFlow<UiState<Info>>(UiState.Loading())
    val infoState = _infoState.asStateFlow()

    private val _bookmarkStatus = MutableStateFlow<BookmarkStatus>(BookmarkStatus.Default)
    val bookmarkStatus = _bookmarkStatus.asStateFlow()

    private val _sessionStatus = MutableStateFlow(false)
    val sessionStatus = _sessionStatus.asStateFlow()

    private val id: String = checkNotNull(savedStateHandle[INFO_ID_KEY])

    init {
        getBookInfo()
        getUser()
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

    fun addBookToBookmark(bookmark: Bookmark, onSuccess: () -> Unit) {
        viewModelScope.launch {
            kotlin.runCatching {
                bookmarksRepository.addBookToBookmark(bookmark)
            }.onSuccess {
                onSuccess()
            }.onFailure {
                it.printStackTrace()
                it.message?.let { message ->
                    Log.e("bookmark", message)
                }
            }
        }
    }

    fun getUser() {
        viewModelScope.launch {
            userRepository.userSessionFlow.collectLatest { session ->
                _sessionStatus.value = session.refreshToken.isNotEmpty() && session.user != null
            }
        }
    }

    fun changeBookmarkStatus(bookmarkStatus: BookmarkStatus) {
        when (bookmarkStatus) {
            is BookmarkStatus.Success -> {
                _bookmarkStatus.value = bookmarkStatus
            }
            is BookmarkStatus.AuthFailure -> {
                _bookmarkStatus.value = bookmarkStatus
            }
            else -> {
                _bookmarkStatus.value = bookmarkStatus
            }
        }
    }

    companion object {
        const val INFO_ID_KEY = "id"
    }
}