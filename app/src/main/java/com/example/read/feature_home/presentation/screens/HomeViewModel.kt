package com.example.read.feature_home.presentation.screens

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.example.read.feature_home.domain.models.BookItem
import com.example.read.feature_home.domain.models.RecommendationItem
import com.example.read.feature_home.domain.repositories.BooksRepository
import com.example.read.utils.state_holders.UiState
import com.example.read.utils.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val bookRepository: BooksRepository,
) : BaseViewModel() {

    private val _booksState = MutableStateFlow(PagingData.empty<BookItem>())
    val booksState = _booksState.asStateFlow()

    private val _recommendationsState = MutableStateFlow(PagingData.empty<RecommendationItem>())
    val recommendationsState = _recommendationsState.asStateFlow()

    private val _slidesState =
        MutableStateFlow<UiState<List<RecommendationItem>>>(UiState.Loading())
    val slidesState = _slidesState.asStateFlow()

    private val searchQueryState = savedStateHandle.getStateFlow(SEARCH_QUERY_KEY, String())

    init {
        searchQueryState.flatMapLatest { searchQuery ->
            bookRepository.fetchBooks(searchQuery)
        }.collectFlowAsPaging(_booksState)
    }

    fun setSearchQuery(searchQuery: String) {
        savedStateHandle[SEARCH_QUERY_KEY] = searchQuery
    }

    companion object {
        const val SEARCH_QUERY_KEY = "search_query"
    }
}