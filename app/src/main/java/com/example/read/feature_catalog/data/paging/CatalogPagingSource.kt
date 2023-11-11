package com.example.read.feature_catalog.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.read.feature_home.data.remote.dtos.BookItemDto
import com.example.read.feature_home.data.remote.dtos.toBookItemDto
import com.example.read.feature_home.domain.models.BookItem
import com.example.read.utils.extensions.await
import com.parse.ParseException
import com.parse.ParseObject
import java.io.IOException

class CatalogPagingSource(
    private val objects: suspend () -> List<ParseObject>,
    private val nextObjects: suspend () -> List<ParseObject>,
    private val map: (BookItemDto) -> BookItem
) : PagingSource<List<ParseObject>, BookItem>() {

    override fun getRefreshKey(state: PagingState<List<ParseObject>, BookItem>): List<ParseObject>? =
        null

    override suspend fun load(params: LoadParams<List<ParseObject>>): LoadResult<List<ParseObject>, BookItem> {
        return try {
            val currentPage = params.key ?: objects()

            val nextPage = nextObjects().takeIf {
                it.isNotEmpty()
            }

            val data = currentPage.map {
                it.toBookItemDto()
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