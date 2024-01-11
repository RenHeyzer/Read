package com.example.read.feature_home.domain.repositories

import androidx.paging.PagingData
import com.example.read.common.domain.models.Book
import kotlinx.coroutines.flow.Flow

interface BooksRepository {

    fun fetchBooks(): Flow<PagingData<Book>>
}