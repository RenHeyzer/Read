package com.example.read.main.presentation.activity

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.example.read.feature_home.domain.models.BookItem
import com.example.read.feature_home.domain.repositories.BooksRepository
import com.example.read.feature_home.presentation.screens.HomeViewModel
import com.example.read.feature_profile.domain.models.UserSession
import com.example.read.feature_profile.domain.repositories.UserRepository
import com.example.read.feature_profile.presentation.screens.UserUiState
import com.example.read.main.data.local.preferences.UserSessionManager
import com.example.read.utils.base.BaseViewModel
import com.example.read.utils.mappers.asData
import com.example.read.utils.mappers.asDomain
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val userRepository: UserRepository,
    private val userSessionManager: UserSessionManager,
    private val bookRepository: BooksRepository,
) : BaseViewModel() {

    private val _searchResultState = MutableStateFlow(PagingData.empty<BookItem>())
    val searchResultState = _searchResultState.asStateFlow()

    private val searchQueryState =
        savedStateHandle.getStateFlow(HomeViewModel.SEARCH_QUERY_KEY, String())

    init {
//        refreshUser()
        searchQueryState.flatMapLatest { searchQuery ->
            if (searchQuery.isNotEmpty()) {
                bookRepository.getBooks(searchQuery)
            } else flowOf(PagingData.empty())
        }.collectFlowAsPaging(_searchResultState)
    }

    fun setSearchQuery(searchQuery: String) {
        savedStateHandle[HomeViewModel.SEARCH_QUERY_KEY] = searchQuery
    }

    fun updateUserSession(userSession: io.github.jan.supabase.gotrue.user.UserSession) {
        viewModelScope.launch {
            userSessionManager.updateUserSessionPreferences(userSession)
        }
    }

    fun refreshUser() {
        viewModelScope.launch {
            userRepository.userSessionFlow.collectLatest { session ->
                if (session.refreshToken.isNotEmpty()) {
                    launch {
                        kotlin.runCatching {
                            userRepository.getRefreshedSession(session.refreshToken)
                        }.onSuccess { session ->
//                            updateUserSession(session)
                        }.onFailure {
                            it.printStackTrace()
                        }
                    }
                }
            }
        }
    }
}