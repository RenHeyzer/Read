package com.example.read.books.data.remote.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.read.books.data.remote.dtos.ItemDto
import com.example.read.books.data.remote.sources.BookRemoteDataSource
import com.example.read.books.domain.models.Item
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.delay
import kotlinx.coroutines.tasks.await
import java.io.IOException

class BookPagingSource(
    private val bookRemoteDataSource: BookRemoteDataSource,
    private val map: (ItemDto) -> Item
) : PagingSource<QuerySnapshot, Item>() {

    override fun getRefreshKey(state: PagingState<QuerySnapshot, Item>): QuerySnapshot? = null

    override suspend fun load(params: LoadParams<QuerySnapshot>): LoadResult<QuerySnapshot, Item> {
        return try {
            delay(2000)
            val currentPage = params.key ?: bookRemoteDataSource.queryBooks().get().await()
            val lastVisibleItem = currentPage.documents.last()
            val nextPage =
                bookRemoteDataSource.queryBooks().startAfter(lastVisibleItem).get().await()
            val data = currentPage.toObjects(ItemDto::class.java)
            LoadResult.Page(
                data = data.map(map),
                prevKey = null,
                nextKey = nextPage
            )
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}