package com.example.read.feature_home.data.repositories

import androidx.paging.map
import com.example.read.feature_detail.data.remote.dtos.ChapterDto
import com.example.read.feature_detail.domain.models.Chapter
import com.example.read.feature_home.data.paging.BooksPagingSource
import com.example.read.feature_home.data.paging.RecommendationsPagingSource
import com.example.read.feature_home.data.remote.dtos.BookItemDto
import com.example.read.feature_home.data.remote.dtos.RecommendationItemDto
import com.example.read.feature_home.data.remote.sources.books.BooksRemoteDataSource
import com.example.read.feature_home.data.remote.sources.recommendations.RecommendationsRemoteDataSource
import com.example.read.feature_home.domain.models.BookItem
import com.example.read.feature_home.domain.models.RecommendationItem
import com.example.read.feature_home.domain.repositories.BooksRepository
import com.example.read.utils.base.BaseRepository
import com.example.read.utils.dispatchers.AppDispatchers
import com.example.read.utils.mappers.Mapper
import com.example.read.utils.time.TimeDifference
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.supervisorScope
import kotlinx.datetime.toKotlinInstant
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

    override fun getBooks(searchQuery: String) = doPagingFlowRequest(
        pagingSource = BooksPagingSource { offset, limit ->
            booksRemoteDataSource.getBooks(offset, limit, searchQuery)
        }
    ).map { pagingData ->
        pagingData.map {
            bookItemMapper.to(model = it).copy(
                latestChapter = it.latestChapter?.latest?.let { latest ->
                    chapterMapper.to(model = latest).copy(
                        timeSinceRelease = determineTimeSinceLatestChapterRelease(latest)
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
                        endDate = Calendar.getInstance().toInstant().toKotlinInstant()
                    )
                }
            }.await()
        }
    }

    override fun getRecommendations() = doPagingFlowRequest(
        pagingSource = RecommendationsPagingSource(recommendationsRemoteDataSource),
        pageSize = 10
    ).map { pagingData ->
        pagingData.map {
            recommendationItemMapper.to(model = it)
        }
    }

    override fun getRecommendationSlides() = doRequest(
        request = {
            recommendationsRemoteDataSource.getRecommendationSlides()
        },
        map = { slides ->
            slides.map {
                recommendationItemMapper.to(model = it)
            }
        }
    )
}