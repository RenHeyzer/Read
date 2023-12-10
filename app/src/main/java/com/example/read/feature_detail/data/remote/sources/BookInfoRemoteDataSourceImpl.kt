package com.example.read.feature_detail.data.remote.sources

import com.example.read.feature_bookmarks.data.remote.dtos.BookmarkDto
import com.example.read.feature_detail.data.remote.dtos.InfoDto
import com.example.read.utils.constants.Constants
import io.github.jan.supabase.postgrest.Postgrest
import javax.inject.Inject

class BookInfoRemoteDataSourceImpl @Inject constructor(
    private val postgrest: Postgrest
) : BookInfoRemoteDataSource {

    override suspend fun getBookInfo(id: String) = postgrest[Constants.INFO_TABLE_NAME].select {
        eq("id", id)
    }.decodeSingle<InfoDto>()
}