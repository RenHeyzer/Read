package com.example.read.feature_home.presentation.screens

import androidx.lifecycle.SavedStateHandle
import androidx.paging.PagingData
import com.example.read.feature_home.domain.models.BookItem
import com.example.read.feature_home.domain.models.RecommendationItem
import com.example.read.feature_home.domain.repositories.BooksRepository
import com.example.read.feature_home.presentation.models.LoadingState
import com.example.read.feature_home.presentation.models.LoadingType
import com.example.read.utils.base.BaseViewModel
import com.example.read.utils.state_holders.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
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

    private val _loadingState = MutableStateFlow(LoadingState())
    val loadingState = _loadingState.asStateFlow()

    init {
        bookRepository.getRecommendationSlides().collectFlowAsState(_slidesState)
        bookRepository.getRecommendations().collectFlowAsPaging(_recommendationsState)
        bookRepository.getBooks("").collectFlowAsPaging(_booksState)
    }

    fun updateBooksLoadingState(type: LoadingType) {
        when (type) {
            is LoadingType.Books -> _loadingState.value =
                loadingState.value.copy(booksLoading = type.value)

            is LoadingType.Recommendations -> _loadingState.value =
                loadingState.value.copy(recommendationsLoading = type.value)

            is LoadingType.Slides -> _loadingState.value =
                loadingState.value.copy(slidesLoading = type.value)
        }
    }

    companion object {
        const val SEARCH_QUERY_KEY = "search_query"
    }
}