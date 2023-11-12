package com.example.read.feature_home.data.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.read.feature_home.data.remote.dtos.BookItemDto
import com.example.read.feature_home.data.remote.sources.books.BooksRemoteDataSource
import io.github.jan.supabase.exceptions.HttpRequestException
import io.github.jan.supabase.exceptions.RestException
import io.ktor.client.plugins.HttpRequestTimeoutException
import java.io.IOException

class BooksPagingSource(
    private val booksRemoteDataSource: BooksRemoteDataSource,
    private val searchQuery: String,
) : PagingSource<Long, BookItemDto>() {
    override fun getRefreshKey(state: PagingState<Long, BookItemDto>): Long? =
        null

    override suspend fun load(params: LoadParams<Long>): LoadResult<Long, BookItemDto> {
        return try {
            val currentPage = params.key ?: 0

            Log.e("key", currentPage.toString())

            val comics =
                booksRemoteDataSource.getBooks(
                    offset = currentPage,
                    limit = params.loadSize.toLong(),
                )

            val nextKey = if (comics.isNotEmpty()) currentPage + comics.size else null

            LoadResult.Page(
                data = comics,
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