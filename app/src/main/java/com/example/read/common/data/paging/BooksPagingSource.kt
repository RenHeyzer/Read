package com.example.read.common.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.read.common.data.remote.models.BookDto
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.tasks.await
import java.io.IOException

class BooksPagingSource(
    private val request: suspend (limit: Long, startAfter: DocumentSnapshot?) -> Query,
) : PagingSource<QuerySnapshot, BookDto>() {
    override fun getRefreshKey(state: PagingState<QuerySnapshot, BookDto>): QuerySnapshot? =
        null

    override suspend fun load(params: LoadParams<QuerySnapshot>): LoadResult<QuerySnapshot, BookDto> {
        return try {
            val currentPage =
                params.key ?: request(params.loadSize.toLong(), null).get().await()
            val lastVisibleItem = currentPage.documents.last()
            val nextPage =
                request(params.loadSize.toLong(), lastVisibleItem).get().await()
            val data = currentPage.toObjects(BookDto::class.java)

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