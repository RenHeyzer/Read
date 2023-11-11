package com.example.read.feature_home.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.read.feature_home.data.remote.dtos.RecommendationItemDto
import com.example.read.feature_home.data.remote.dtos.toRecommendationItemDto
import com.example.read.feature_home.data.remote.sources.recommendations.RecommendationsRemoteDataSource
import com.example.read.feature_home.domain.models.RecommendationItem
import com.example.read.utils.extensions.await
import com.parse.ParseException
import com.parse.ParseObject
import java.io.IOException

class RecommendationsPagingSource(
    private val recommendationsRemoteDataSource: RecommendationsRemoteDataSource,
    private val map: (RecommendationItemDto) -> RecommendationItem
) : PagingSource<List<ParseObject>, RecommendationItem>() {

    override fun getRefreshKey(state: PagingState<List<ParseObject>, RecommendationItem>): List<ParseObject>? =
        null

    override suspend fun load(params: LoadParams<List<ParseObject>>): LoadResult<List<ParseObject>, RecommendationItem> {
        return try {
            val queryRecommendations =
                recommendationsRemoteDataSource.queryRecommendations(limit = params.loadSize, skip = 0)

            val currentPage = params.key ?: queryRecommendations

            val nextPage =
                recommendationsRemoteDataSource.queryRecommendations(limit = params.loadSize, skip = params.loadSize).takeIf {
                    it.isNotEmpty()
                }

            val data = currentPage.map {
                it.toRecommendationItemDto()
            }
            LoadResult.Page(
                data = data.map {
                    map(it)
                },
                prevKey = null,
                nextKey = nextPage
            )
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: ParseException) {
            LoadResult.Error(e)
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}