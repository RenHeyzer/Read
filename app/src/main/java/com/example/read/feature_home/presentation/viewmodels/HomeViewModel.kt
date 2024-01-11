package com.example.read.feature_home.presentation.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.paging.PagingData
import com.example.read.common.domain.models.Book
import com.example.read.common.presentation.base.BaseViewModel
import com.example.read.common.presentation.utils.UiState
import com.example.read.feature_home.domain.models.RecommendationItem
import com.example.read.feature_home.domain.repositories.BooksRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val bookRepository: BooksRepository,
) : BaseViewModel() {

    private val _booksState = MutableStateFlow(PagingData.empty<Book>())
    val booksState = _booksState.asStateFlow()

    private val _recommendationsState = MutableStateFlow(PagingData.empty<RecommendationItem>())
    val recommendationsState = _recommendationsState.asStateFlow()

    private val _slidesState =
        MutableStateFlow<UiState<List<RecommendationItem>>>(UiState.Loading())
    val slidesState = _slidesState.asStateFlow()

    init {
        bookRepository.fetchBooks().collectFlowAsPaging(_booksState)
    }
}