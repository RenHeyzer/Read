package com.example.read.feature_catalog.presentation.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.paging.PagingData
import com.example.read.common.domain.models.Book
import com.example.read.common.presentation.base.BaseViewModel
import com.example.read.feature_catalog.domain.repositories.CatalogRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class CatalogViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val catalogRepository: CatalogRepository
) : BaseViewModel() {

    private val _comicsState = MutableStateFlow<PagingData<Book>>(PagingData.empty())
    val comicsState = _comicsState.asStateFlow()

    private val _mangaState = MutableStateFlow<PagingData<Book>>(PagingData.empty())
    val mangaState = _mangaState.asStateFlow()

    init {
        catalogRepository.getComicsCatalog().collectFlowAsPaging(_comicsState)
        catalogRepository.getMangaCatalog().collectFlowAsPaging(_mangaState)
    }
}