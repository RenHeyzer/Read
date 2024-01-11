package com.example.read.feature_catalog.domain.repositories

import androidx.paging.PagingData
import com.example.read.common.domain.models.Book
import kotlinx.coroutines.flow.Flow

interface CatalogRepository {

    fun getMangaCatalog(): Flow<PagingData<Book>>

    fun getComicsCatalog(): Flow<PagingData<Book>>
}