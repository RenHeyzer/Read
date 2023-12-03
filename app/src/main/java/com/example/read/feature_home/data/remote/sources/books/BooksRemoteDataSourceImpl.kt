package com.example.read.feature_home.data.remote.sources.books

import com.example.read.feature_home.data.remote.dtos.BookItemDto
import com.example.read.utils.constants.Constants
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.query.Columns
import io.github.jan.supabase.postgrest.query.TextSearchType
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BooksRemoteDataSourceImpl @Inject constructor(
    private val postgrest: Postgrest
) : BooksRemoteDataSource {

    override suspend fun getBooks(offset: Long, limit: Long, searchQuery: String) =
        postgrest[Constants.BOOKS_TABLE_NAME].select {
            if (searchQuery.isNotEmpty()) {
                gte("title", searchQuery)
            }
            range(offset, limit)
        }.decodeList<BookItemDto>()
}