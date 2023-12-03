package com.example.read.feature_bookmarks.data.remote.sources

import com.example.read.feature_bookmarks.data.remote.dtos.BookmarkDto
import com.example.read.feature_bookmarks.data.remote.dtos.BookmarkResponseDto
import com.example.read.feature_bookmarks.domain.models.BookmarkType
import com.example.read.feature_home.data.remote.dtos.BookItemDto

interface BookmarksRemoteDataSource {

    suspend fun getBookmarks(offset: Long, limit: Long, bookmarkType: BookmarkType): List<BookmarkResponseDto>

    suspend fun addBookToBookmark(bookmark: BookmarkDto)
}