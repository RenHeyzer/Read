package com.example.read.feature_bookmarks.data.remote.sources

import com.example.read.feature_bookmarks.data.remote.dtos.BookmarkDto
import com.example.read.feature_bookmarks.data.remote.dtos.BookmarkResponseDto
import com.example.read.feature_bookmarks.domain.models.BookmarkType
import com.example.read.feature_home.data.remote.dtos.BookItemDto
import com.example.read.utils.constants.Constants
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.query.Columns
import javax.inject.Inject

class BookmarksRemoteDataSourceImpl @Inject constructor(
    private val postgrest: Postgrest,
) : BookmarksRemoteDataSource {

    override suspend fun getBookmarks(offset: Long, limit: Long, bookmarkType: BookmarkType) = postgrest[Constants.BOOKMARKS_TABLE_NAME].select(
        Columns.raw("books(*)")
    ) {
        if (bookmarkType != BookmarkType.All) {
            eq(column = "bookmark_type", value = bookmarkType.type)
        }
        range(offset, limit)
    }.decodeList<BookmarkResponseDto>()

    override suspend fun addBookToBookmark(bookmark: BookmarkDto) {
        postgrest[Constants.BOOKMARKS_TABLE_NAME].insert(bookmark)
    }
}