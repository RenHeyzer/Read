package com.example.read.feature_catalog.data.remote.sources

import com.example.read.feature_home.data.remote.dtos.BookItemDto

interface CatalogRemoteDataSource {

    suspend fun getComics(offset: Long, limit: Long, searchQuery: String): List<BookItemDto>

    suspend fun getManga(offset: Long, limit: Long, searchQuery: String): List<BookItemDto>
}