package com.example.read.feature_home.data.remote.sources.books

import com.example.read.feature_home.data.remote.dtos.BookItemDto
import com.parse.ParseObject

interface BooksRemoteDataSource {

    suspend fun fetchComics(limit: Int, skip: Int, searchQuery: String): List<ParseObject>

    suspend fun fetchManga(limit: Int, skip: Int, searchQuery: String): List<ParseObject>

    suspend fun addBook(item: BookItemDto)
}