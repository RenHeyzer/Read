package com.example.read.feature_catalog.data.remote.sources

import com.example.read.feature_home.data.remote.dtos.BookItemDto
import com.example.read.utils.constants.Constants
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.query.TextSearchType
import javax.inject.Inject

class CatalogRemoteDataSourceImpl @Inject constructor(
    private val postgrest: Postgrest
) : CatalogRemoteDataSource {

    override suspend fun getComics(offset: Long, limit: Long, searchQuery: String) =
        postgrest[Constants.COMICS_TABLE_NAME].select {
            range(offset, limit)
        }.decodeList<BookItemDto>()

    override suspend fun getManga(offset: Long, limit: Long, searchQuery: String) =
        postgrest[Constants.MANGA_TABLE_NAME].select {
            range(offset, limit)
        }.decodeList<BookItemDto>()
}