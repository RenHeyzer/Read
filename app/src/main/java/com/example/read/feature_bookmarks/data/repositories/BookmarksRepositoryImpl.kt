package com.example.read.feature_bookmarks.data.repositories

import androidx.paging.map
import com.example.read.feature_bookmarks.data.remote.dtos.BookmarkDto
import com.example.read.feature_bookmarks.data.remote.sources.BookmarksRemoteDataSource
import com.example.read.feature_bookmarks.domain.models.Bookmark
import com.example.read.feature_bookmarks.domain.models.BookmarkType
import com.example.read.feature_bookmarks.domain.repositories.BookmarksRepository
import com.example.read.feature_home.data.paging.BooksPagingSource
import com.example.read.feature_home.data.remote.dtos.BookItemDto
import com.example.read.feature_home.domain.models.BookItem
import com.example.read.utils.base.BaseRepository
import com.example.read.utils.dispatchers.AppDispatchers
import com.example.read.utils.mappers.BookmarkMapper
import com.example.read.utils.mappers.Mapper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class BookmarksRepositoryImpl @Inject constructor(
    private val appDispatchers: AppDispatchers,
    private val bookmarksRemoteDataSource: BookmarksRemoteDataSource,
    private val bookItemMapper: Mapper<BookItemDto, BookItem>,
    private val bookmarkMapper: BookmarkMapper
) : BaseRepository(appDispatchers), BookmarksRepository {

    override fun getBookmarks(bookmarkType: BookmarkType) = doPagingFlowRequest(
        pagingSource = BooksPagingSource { offset, limit ->
            bookmarksRemoteDataSource.getBookmarks(offset, limit, bookmarkType).map {
                it.item
            }
        }
    ).map { pagingData ->
        pagingData.map {
            bookItemMapper.to(it)
        }
    }

    override suspend fun addBookToBookmark(bookmark: Bookmark, upsert: Boolean) {
        withContext(appDispatchers.io) {
            bookmarksRemoteDataSource.addBookToBookmark(bookmarkMapper.from(bookmark), upsert)
        }
    }

    override suspend fun deleteBookInBookmark(id: String) = withContext(appDispatchers.io) {
        val result = bookmarksRemoteDataSource.deleteBookInBookmark(id)
        bookmarkMapper.to(result)
    }

    override suspend fun checkBookInBookmarks(bookId: String, userId: String) =
        withContext(appDispatchers.io) {
            val result = bookmarksRemoteDataSource.checkBookInBookmarks(bookId, userId)
            if (result != null) bookmarkMapper.to(result) else null
        }

    override suspend fun connectToBookmarksRealtime(
        scope: CoroutineScope,
        onDelete: (suspend (deletedId: String) -> Unit)?,
        onInsert: (suspend (bookmark: Bookmark) -> Unit)?,
        onSelect: (suspend (bookmark: Bookmark) -> Unit)?,
        onUpdate: (suspend (bookmark: Bookmark) -> Unit)?
    ) {
        bookmarksRemoteDataSource.connectToBookmarksRealtime(
            scope = scope,
            onDelete = onDelete,
            onInsert = { bookmark ->
                onInsert?.let {
                    it(bookmarkMapper.to(bookmark))
                }
            },
            onSelect = { bookmark ->
                onSelect?.let {
                    it(bookmarkMapper.to(bookmark))
                }
            },
            onUpdate = { bookmark ->
                onUpdate?.let {
                    it(bookmarkMapper.to(bookmark))
                }
            }
        )
    }
}