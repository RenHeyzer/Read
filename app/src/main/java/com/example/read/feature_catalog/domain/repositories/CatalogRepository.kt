package com.example.read.feature_catalog.domain.repositories

import androidx.paging.PagingData
import com.example.read.feature_home.domain.models.BookItem
import kotlinx.coroutines.flow.Flow

interface CatalogRepository {

    fun getComics(searchQuery: String): Flow<PagingData<BookItem>>

    fun getManga(searchQuery: String): Flow<PagingData<BookItem>>
}