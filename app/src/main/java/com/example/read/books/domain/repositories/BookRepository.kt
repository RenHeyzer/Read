package com.example.read.books.domain.repositories

import androidx.paging.PagingData
import com.example.read.books.domain.models.Item
import kotlinx.coroutines.flow.Flow

interface BookRepository {

    fun fetchBooks(): Flow<PagingData<Item>>

//    suspend fun deleteAllDocuments()
}