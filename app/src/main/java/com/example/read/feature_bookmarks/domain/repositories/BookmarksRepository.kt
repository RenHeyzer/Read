package com.example.read.feature_bookmarks.domain.repositories

import androidx.paging.PagingData
import com.example.read.feature_bookmarks.domain.models.Bookmark
import com.example.read.feature_bookmarks.domain.models.BookmarkType
import com.example.read.feature_home.domain.models.BookItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow

interface BookmarksRepository {

    fun getBookmarks(bookmarkType: BookmarkType): Flow<PagingData<BookItem>>

    suspend fun addBookToBookmark(bookmark: Bookmark, upsert: Boolean = false)

    suspend fun deleteBookInBookmark(id: String): Bookmark?

    suspend fun checkBookInBookmarks(bookId: String, userId: String): Bookmark?

    suspend fun connectToBookmarksRealtime(
        scope: CoroutineScope,
        onDelete: (suspend (deletedId: String) -> Unit)? = null,
        onInsert: (suspend (bookmark: Bookmark) -> Unit)? = null,
        onSelect: (suspend (bookmark: Bookmark) -> Unit)? = null,
        onUpdate: (suspend (bookmark: Bookmark) -> Unit)? = null,
    )
}