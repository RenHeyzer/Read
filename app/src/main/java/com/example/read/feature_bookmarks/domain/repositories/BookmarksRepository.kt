package com.example.read.feature_bookmarks.domain.repositories

import androidx.paging.PagingData
import com.example.read.feature_bookmarks.domain.models.Bookmark
import com.example.read.feature_bookmarks.domain.models.BookmarkType
import com.example.read.feature_home.domain.models.BookItem
import kotlinx.coroutines.flow.Flow

interface BookmarksRepository {

    fun getBookmarks(bookmarkType: BookmarkType): Flow<PagingData<BookItem>>

    suspend fun addBookToBookmark(bookmark: Bookmark, upsert: Boolean = false)

    suspend fun checkBookInBookmarks(id: String): Bookmark?
}