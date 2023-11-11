package com.example.read.feature_home.domain.repositories

import androidx.paging.PagingData
import com.example.read.feature_home.domain.models.BookItem
import com.example.read.feature_home.domain.models.RecommendationItem
import com.example.read.utils.state_holders.Either
import kotlinx.coroutines.flow.Flow

interface BooksRepository {

    fun fetchBooks(searchQuery: String): Flow<PagingData<BookItem>>

    fun fetchRecommendations(): Flow<PagingData<RecommendationItem>>

    fun fetchRecommendationSlides(): Flow<Either<String, List<RecommendationItem>>>

    suspend fun addBooks(item: BookItem)

//    suspend fun deleteBooks()
}