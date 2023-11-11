package com.example.read.feature_home.data.repositories

import androidx.paging.map
import com.example.read.feature_detail.data.remote.dtos.ChapterDto
import com.example.read.feature_detail.domain.models.Chapter
import com.example.read.feature_home.data.paging.BooksPagingSource
import com.example.read.feature_home.data.paging.RecommendationsPagingSource
import com.example.read.feature_home.data.remote.dtos.BookItemDto
import com.example.read.feature_home.data.remote.dtos.RecommendationItemDto
import com.example.read.feature_home.data.remote.dtos.toBookItemDto
import com.example.read.feature_home.data.remote.dtos.toRecommendationItemDto
import com.example.read.feature_home.data.remote.sources.books.BooksRemoteDataSource
import com.example.read.feature_home.data.remote.sources.recommendations.RecommendationsRemoteDataSource
import com.example.read.feature_home.domain.models.BookItem
import com.example.read.feature_home.domain.models.RecommendationItem
import com.example.read.feature_home.domain.repositories.BooksRepository
import com.example.read.utils.AppDispatchers
import com.example.read.utils.TimeDifference
import com.example.read.utils.base.BaseRepository
import com.example.read.utils.mapper.Mapper
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.supervisorScope
import kotlinx.coroutines.withContext
import java.util.Calendar
import javax.inject.Inject

@ViewModelScoped
class BooksRepositoryImpl @Inject constructor(
    private val appDispatchers: AppDispatchers,
    private val booksRemoteDataSource: BooksRemoteDataSource,
    private val recommendationsRemoteDataSource: RecommendationsRemoteDataSource,
    private val bookItemMapper: Mapper<BookItemDto, BookItem>,
    private val recommendationItemMapper: Mapper<RecommendationItemDto, RecommendationItem>,
    private val chapterMapper: Mapper<ChapterDto, Chapter>,
    private val timeDifference: TimeDifference
) : BaseRepository(appDispatchers), BooksRepository {

    override fun fetchBooks(searchQuery: String) = doPagingFlowRequest(
        pagingSource = BooksPagingSource(booksRemoteDataSource, searchQuery)
    ).map { pagingData ->
        pagingData.map {
            val bookItemDto = it.toBookItemDto()
            bookItemMapper.to(model = bookItemDto).copy(
                latestChapter = bookItemDto.latestChapter?.let { latestChapter ->
                    chapterMapper.to(model = latestChapter).copy(
                        timeSinceRelease = determineTimeSinceLatestChapterRelease(latestChapter)
                    )
                }
            )
        }
    }

    suspend fun determineTimeSinceLatestChapterRelease(chapterDto: ChapterDto?): String? {
        return supervisorScope {
            async(appDispatchers.default) {
                chapterDto?.releaseDate?.let { startDate ->
                    timeDifference.getTimeDifference(
                        startDate = startDate,
                        endDate = Calendar.getInstance().time
                    )
                }
            }.await()
        }
    }

    override fun fetchRecommendations() = doPagingFlowRequest(
        pagingSource = RecommendationsPagingSource(recommendationsRemoteDataSource) {
            recommendationItemMapper.to(model = it)
        },
        pageSize = 10
    )

    override fun fetchRecommendationSlides() = doRequest(
        request = {
            recommendationsRemoteDataSource.querySlides().map {
                it.toRecommendationItemDto()
            }
        },
        map = {
            it.map { item ->
                recommendationItemMapper.to(model = item)
            }
        }
    )

    override suspend fun addBooks(item: BookItem) {
        withContext(appDispatchers.io) {
            booksRemoteDataSource.addBook(bookItemMapper.from(model = item))
        }
    }
}