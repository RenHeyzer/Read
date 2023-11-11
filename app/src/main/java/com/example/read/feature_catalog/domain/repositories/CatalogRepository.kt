package com.example.read.feature_catalog.domain.repositories

import androidx.paging.PagingData
import com.example.read.feature_home.domain.models.BookItem
import kotlinx.coroutines.flow.Flow

interface CatalogRepository {

    fun fetchComics(): Flow<PagingData<BookItem>>

    fun fetchManga(): Flow<PagingData<BookItem>>
}