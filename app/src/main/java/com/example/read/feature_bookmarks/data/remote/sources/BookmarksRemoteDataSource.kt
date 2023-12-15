package com.example.read.feature_bookmarks.data.remote.sources

import com.example.read.feature_bookmarks.data.remote.dtos.BookmarkDto
import com.example.read.feature_bookmarks.data.remote.dtos.BookmarkResponseDto
import com.example.read.feature_bookmarks.domain.models.BookmarkType
import com.example.read.feature_home.data.remote.dtos.BookItemDto
import io.github.jan.supabase.realtime.PostgresAction
import io.github.jan.supabase.realtime.RealtimeChannel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow

interface BookmarksRemoteDataSource {

    val bookmarksRealtimeChannel: RealtimeChannel

    suspend fun getBookmarks(
        offset: Long,
        limit: Long,
        bookmarkType: BookmarkType
    ): List<BookmarkResponseDto>

    suspend fun addBookToBookmark(bookmark: BookmarkDto, upsert: Boolean = false)

    suspend fun deleteBookInBookmark(id: String): BookmarkDto

    suspend fun checkBookInBookmarks(bookId: String, userId: String): BookmarkDto?

    suspend fun connectToBookmarksRealtime(
        scope: CoroutineScope,
        onDelete: (suspend (deletedId: String) -> Unit)? = null,
        onInsert: (suspend (bookmark: BookmarkDto) -> Unit)? = null,
        onSelect: (suspend (bookmark: BookmarkDto) -> Unit)? = null,
        onUpdate: (suspend (bookmark: BookmarkDto) -> Unit)? = null,
    )
}