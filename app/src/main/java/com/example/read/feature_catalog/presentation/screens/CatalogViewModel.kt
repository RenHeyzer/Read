package com.example.read.feature_catalog.presentation.screens

import androidx.lifecycle.SavedStateHandle
import androidx.paging.PagingData
import com.example.read.feature_catalog.domain.repositories.CatalogRepository
import com.example.read.feature_home.domain.models.BookItem
import com.example.read.feature_home.presentation.screens.HomeViewModel
import com.example.read.utils.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

@HiltViewModel
class CatalogViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val catalogRepository: CatalogRepository
) : BaseViewModel() {

    private val _comicsState = MutableStateFlow<PagingData<BookItem>>(PagingData.empty())
    val comicsState = _comicsState.asStateFlow()

    private val _mangaState = MutableStateFlow<PagingData<BookItem>>(PagingData.empty())
    val mangaState = _mangaState.asStateFlow()

    private val searchQueryState = savedStateHandle.getStateFlow(HomeViewModel.SEARCH_QUERY_KEY, String())

    init {
        searchQueryState.flatMapLatest { searchQuery ->
            catalogRepository.getComics(searchQuery)
        }.collectFlowAsPaging(_comicsState)

        searchQueryState.flatMapLatest { searchQuery ->
            catalogRepository.getManga(searchQuery)
        }.collectFlowAsPaging(_mangaState)
    }

    fun setSearchQuery(searchQuery: String) {
        savedStateHandle[HomeViewModel.SEARCH_QUERY_KEY] = searchQuery
    }
}