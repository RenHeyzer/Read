package com.example.read.feature_bookmarks.data.remote.sources

import com.example.read.feature_bookmarks.data.remote.dtos.BookmarkDto
import com.example.read.feature_bookmarks.data.remote.dtos.BookmarkResponseDto
import com.example.read.feature_bookmarks.domain.models.BookmarkType
import com.example.read.utils.constants.Constants
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.query.Columns
import io.github.jan.supabase.postgrest.query.FilterOperation
import io.github.jan.supabase.realtime.PostgresAction
import io.github.jan.supabase.realtime.Realtime
import io.github.jan.supabase.realtime.RealtimeChannel
import io.github.jan.supabase.realtime.createChannel
import io.github.jan.supabase.realtime.decodeRecord
import io.github.jan.supabase.realtime.postgresChangeFlow
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.selects.select
import kotlinx.serialization.json.int
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import javax.inject.Inject

class BookmarksRemoteDataSourceImpl @Inject constructor(
    private val postgrest: Postgrest,
    private val realtime: Realtime
) : BookmarksRemoteDataSource {

    override val bookmarksRealtimeChannel: RealtimeChannel =
        realtime.createChannel(Constants.BOOKMARKS_TABLE_NAME)

    override suspend fun getBookmarks(offset: Long, limit: Long, bookmarkType: BookmarkType) =
        postgrest[Constants.BOOKMARKS_TABLE_NAME].select(
            Columns.raw("books(*)")
        ) {
            if (bookmarkType != BookmarkType.All) {
                eq(column = "bookmark_type", value = bookmarkType.type)
            }
            range(offset, limit)
        }.decodeList<BookmarkResponseDto>()

    override suspend fun addBookToBookmark(bookmark: BookmarkDto, upsert: Boolean) {
        postgrest[Constants.BOOKMARKS_TABLE_NAME].insert(
            bookmark,
            onConflict = "id",
            upsert = upsert
        )
    }

    override suspend fun deleteBookInBookmark(id: String) =
        postgrest[Constants.BOOKMARKS_TABLE_NAME].delete {
            eq("id", id)
        }.decodeSingle<BookmarkDto>()

    override suspend fun checkBookInBookmarks(bookId: String, userId: String) =
        postgrest[Constants.BOOKMARKS_TABLE_NAME].select {
            eq("book_id", bookId)
            eq("user_id", userId)
        }.decodeSingleOrNull<BookmarkDto>()

    override suspend fun connectToBookmarksRealtime(
        scope: CoroutineScope,
        onDelete: (suspend (deletedId: String) -> Unit)?,
        onInsert: (suspend (bookmark: BookmarkDto) -> Unit)?,
        onSelect: (suspend (bookmark: BookmarkDto) -> Unit)?,
        onUpdate: (suspend (bookmark: BookmarkDto) -> Unit)?,
    ) {
        realtime.connect()

        bookmarksRealtimeChannel.postgresChangeFlow<PostgresAction>("public") {
            table = Constants.BOOKMARKS_TABLE_NAME
        }.onEach { action ->
            when (action) {
                is PostgresAction.Delete -> onDelete?.let {
                    action.oldRecord["id"]?.let { jsonId ->
                        it(jsonId.jsonObject.toString())
                    }
                }

                is PostgresAction.Insert -> onInsert?.let {
                    it(action.decodeRecord<BookmarkDto>())
                }

                is PostgresAction.Select -> onSelect?.let {
                    it(action.decodeRecord<BookmarkDto>())
                }

                is PostgresAction.Update -> onUpdate?.let {
                    it(action.decodeRecord<BookmarkDto>())
                }
            }
        }.launchIn(scope)
        bookmarksRealtimeChannel.join()
    }
}