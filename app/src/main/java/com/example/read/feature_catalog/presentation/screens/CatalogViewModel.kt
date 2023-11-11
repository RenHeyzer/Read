package com.example.read.feature_catalog.presentation.screens

import androidx.paging.PagingData
import com.example.read.feature_catalog.domain.repositories.CatalogRepository
import com.example.read.feature_home.domain.models.BookItem
import com.example.read.utils.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class CatalogViewModel @Inject constructor(
    private val catalogRepository: CatalogRepository
) : BaseViewModel() {

    private val _comicsState = MutableStateFlow<PagingData<BookItem>>(PagingData.empty())
    val comicsState = _comicsState.asStateFlow()

    private val _mangaState = MutableStateFlow<PagingData<BookItem>>(PagingData.empty())
    val mangaState = _mangaState.asStateFlow()

    init {
        catalogRepository.fetchComics().collectFlowAsPaging(_comicsState)
        catalogRepository.fetchManga().collectFlowAsPaging(_mangaState)
    }
}