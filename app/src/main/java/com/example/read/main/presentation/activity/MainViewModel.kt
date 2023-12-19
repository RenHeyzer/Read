package com.example.read.main.presentation.activity

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.example.read.feature_home.domain.models.BookItem
import com.example.read.feature_home.domain.repositories.BooksRepository
import com.example.read.feature_home.presentation.screens.HomeViewModel
import com.example.read.utils.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.jan.supabase.gotrue.GoTrue
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val gotrue: GoTrue,
    private val bookRepository: BooksRepository,
) : BaseViewModel() {

    private val _searchResultState = MutableStateFlow(PagingData.empty<BookItem>())
    val searchResultState = _searchResultState.asStateFlow()

    private val searchQueryState =
        savedStateHandle.getStateFlow(HomeViewModel.SEARCH_QUERY_KEY, String())

    init {
        searchQueryState.flatMapLatest { searchQuery ->
            if (searchQuery.isNotEmpty()) {
                bookRepository.getBooks(searchQuery)
            } else flowOf(PagingData.empty())
        }.collectFlowAsPaging(_searchResultState)
    }

    fun setSearchQuery(searchQuery: String) {
        savedStateHandle[HomeViewModel.SEARCH_QUERY_KEY] = searchQuery
    }
}