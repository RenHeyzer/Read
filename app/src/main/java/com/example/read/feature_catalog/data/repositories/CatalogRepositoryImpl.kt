package com.example.read.feature_catalog.data.repositories

import androidx.paging.map
import com.example.read.common.data.base.BaseRepository
import com.example.read.common.data.paging.BooksPagingSource
import com.example.read.common.data.remote.models.BookDto
import com.example.read.common.domain.models.BookEntity
import com.example.read.common.mappers.Mapper
import com.example.read.common.utils.AppDispatchers
import com.example.read.feature_catalog.data.remote.sources.CatalogRemoteDataSource
import com.example.read.feature_catalog.domain.repositories.CatalogRepository
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CatalogRepositoryImpl @Inject constructor(
    private val appDispatchers: AppDispatchers,
    private val catalogRemoteDataSource: CatalogRemoteDataSource,
    private val bookItemMapper: Mapper<BookDto, BookEntity>
) : BaseRepository(appDispatchers), CatalogRepository {

    override fun getMangaCatalog() = doPagingFlowRequest(
        pagingSource = BooksPagingSource(
            request = { limit, startAfter ->
                catalogRemoteDataSource.getMangaCatalog(limit, startAfter)
            }
        )
    ).map { items ->
        items.map {
            bookItemMapper.to(it)
        }
    }

    override fun getComicsCatalog() = doPagingFlowRequest(
        pagingSource = BooksPagingSource(
            request = { limit, startAfter ->
                catalogRemoteDataSource.getComicsCatalog(limit, startAfter)
            }
        )
    ).map { items ->
        items.map {
            bookItemMapper.to(it)
        }
    }
}