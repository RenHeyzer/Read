package com.example.read.feature_bookmarks.data.repositories

import androidx.paging.map
import com.example.read.feature_bookmarks.data.remote.sources.BookmarksRemoteDataSource
import com.example.read.feature_bookmarks.domain.models.BookmarkType
import com.example.read.feature_bookmarks.domain.repositories.BookmarksRepository
import com.example.read.feature_home.data.paging.BooksPagingSource
import com.example.read.feature_home.data.remote.dtos.BookItemDto
import com.example.read.feature_home.domain.models.BookItem
import com.example.read.utils.base.BaseRepository
import com.example.read.utils.dispatchers.AppDispatchers
import com.example.read.utils.mappers.Mapper
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class BookmarkRepositoryImpl @Inject constructor(
    private val appDispatchers: AppDispatchers,
    private val bookmarksRemoteDataSource: BookmarksRemoteDataSource,
    private val bookItemMapper: Mapper<BookItemDto, BookItem>
) : BaseRepository(appDispatchers), BookmarksRepository {

    override fun getBookmarks(bookmarkType: BookmarkType) = doPagingFlowRequest(
        pagingSource = BooksPagingSource { offset, limit ->
            bookmarksRemoteDataSource.getBookmarks(offset, limit, bookmarkType)
        }
    ).map { pagingData ->
        pagingData.map {
            bookItemMapper.to(it)
        }
    }
}