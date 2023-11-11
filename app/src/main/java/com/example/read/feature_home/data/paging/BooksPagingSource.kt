package com.example.read.feature_home.data.paging
import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.read.feature_home.data.remote.sources.books.BooksRemoteDataSource
import com.parse.ParseException
import com.parse.ParseObject
import java.io.IOException

class BooksPagingSource(
    private val booksRemoteDataSource: BooksRemoteDataSource,
    private val searchQuery: String,
) : PagingSource<Int, ParseObject>() {
    override fun getRefreshKey(state: PagingState<Int, ParseObject>): Int? =
        null

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ParseObject> {
        return try {
            val currentPage = params.key ?: 0

            Log.e("key", currentPage.toString())

            val comics =
                booksRemoteDataSource.fetchComics(
                    limit = params.loadSize,
                    skip = currentPage,
                    searchQuery
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
        } catch (e: ParseException) {
            e.printStackTrace()
            LoadResult.Error(e)
        } catch (e: Exception) {
            e.printStackTrace()
            LoadResult.Error(e)
        }
    }
}