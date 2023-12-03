package com.example.read.feature_home.data.remote.sources.books

import com.example.read.feature_home.data.remote.dtos.BookItemDto

interface BooksRemoteDataSource {

    suspend fun getBooks(offset: Long, limit: Long, searchQuery: String): List<BookItemDto>
}