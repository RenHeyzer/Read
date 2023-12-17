package com.example.read.feature_home.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.read.feature_home.data.remote.dtos.BookItemDto
import com.example.read.feature_home.data.remote.sources.BooksRemoteDataSource
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.tasks.await
import java.io.IOException

class BooksPagingSource(
    private val booksRemoteDataSource: BooksRemoteDataSource,
    private val searchQuery: String,
) : PagingSource<QuerySnapshot, BookItemDto>() {
    override fun getRefreshKey(state: PagingState<QuerySnapshot, BookItemDto>): QuerySnapshot? =
        null

    override suspend fun load(params: LoadParams<QuerySnapshot>): LoadResult<QuerySnapshot, BookItemDto> {
        return try {
            val currentPage =
                params.key ?: booksRemoteDataSource.getBooks(params.loadSize.toLong()).get().await()
            val lastVisibleItem = currentPage.documents.last()
            val nextPage =
                booksRemoteDataSource.getBooks(params.loadSize.toLong()).startAfter(lastVisibleItem)
                    .get().await()
            val data = currentPage.toObjects(BookItemDto::class.java)

            LoadResult.Page(
                data = data,
                prevKey = null,
                nextKey = nextPage
            )
        } catch (e: IOException) {
            e.printStackTrace()
            LoadResult.Error(e)
        } catch (e: Exception) {
            e.printStackTrace()
            LoadResult.Error(e)
        }
    }
}