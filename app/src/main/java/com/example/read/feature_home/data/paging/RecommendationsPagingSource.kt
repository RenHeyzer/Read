package com.example.read.feature_home.data.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.read.feature_home.data.remote.dtos.RecommendationItemDto
import com.example.read.feature_home.data.remote.sources.recommendations.RecommendationsRemoteDataSource
import io.github.jan.supabase.exceptions.HttpRequestException
import io.github.jan.supabase.exceptions.RestException
import io.ktor.client.plugins.HttpRequestTimeoutException
import java.io.IOException

class RecommendationsPagingSource(
    private val recommendationsRemoteDataSource: RecommendationsRemoteDataSource,
) : PagingSource<Long, RecommendationItemDto>() {

    override fun getRefreshKey(state: PagingState<Long, RecommendationItemDto>): Long? = null

    override suspend fun load(params: LoadParams<Long>): LoadResult<Long, RecommendationItemDto> {
        return try {
            val currentPage = params.key ?: 0L

            Log.e("key", currentPage.toString())

            val recommendations =
                recommendationsRemoteDataSource.getRecommendations(
                    offset = currentPage,
                    limit = params.loadSize.toLong(),
                )

            val nextKey = if (recommendations.isNotEmpty()) currentPage + recommendations.size.toLong() else null

            LoadResult.Page(
                data = recommendations,
                prevKey = null,
                nextKey = nextKey
            )
        } catch (e: IOException) {
            e.printStackTrace()
            LoadResult.Error(e)
        } catch (e: RestException) {
            e.printStackTrace()
            LoadResult.Error(e)
        } catch (e: HttpRequestTimeoutException) {
            e.printStackTrace()
            LoadResult.Error(e)
        } catch (e: HttpRequestException) {
            e.printStackTrace()
            LoadResult.Error(e)
        } catch (e: Exception) {
            e.printStackTrace()
            LoadResult.Error(e)
        }
    }
}