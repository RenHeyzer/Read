package com.example.read.feature_detail.data.repositories

import com.example.read.feature_bookmarks.data.remote.dtos.BookmarkDto
import com.example.read.feature_bookmarks.data.remote.sources.BookmarksRemoteDataSource
import com.example.read.feature_bookmarks.domain.models.Bookmark
import com.example.read.feature_detail.data.remote.dtos.InfoDto
import com.example.read.feature_detail.data.remote.sources.BookInfoRemoteDataSource
import com.example.read.feature_detail.domain.models.Info
import com.example.read.feature_detail.domain.repositories.BookInfoRepository
import com.example.read.utils.base.BaseRepository
import com.example.read.utils.dispatchers.AppDispatchers
import com.example.read.utils.mappers.Mapper
import kotlinx.coroutines.withContext
import javax.inject.Inject

class BookInfoRepositoryImpl @Inject constructor(
    private val appDispatchers: AppDispatchers,
    private val bookInfoRemoteDataSource: BookInfoRemoteDataSource,
    private val bookmarksRemoteDataSource: BookmarksRemoteDataSource,
    private val infoMapper: Mapper<InfoDto, Info>,
    private val bookmarkMapper: Mapper<BookmarkDto, Bookmark>
) : BaseRepository(appDispatchers), BookInfoRepository {

    override suspend fun getBookInfo(id: String) =
        withContext(appDispatchers.io) {
            infoMapper.to(model = bookInfoRemoteDataSource.getBookInfo(id))
        }
}