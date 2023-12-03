package com.example.read.feature_catalog.data.repositories

import androidx.paging.map
import com.example.read.feature_catalog.data.remote.sources.CatalogRemoteDataSource
import com.example.read.feature_catalog.domain.repositories.CatalogRepository
import com.example.read.feature_home.data.paging.BooksPagingSource
import com.example.read.utils.base.BaseRepository
import com.example.read.utils.dispatchers.AppDispatchers
import com.example.read.utils.mappers.BookItemMapper
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@ViewModelScoped
class CatalogRepositoryImpl @Inject constructor(
    private val appDispatchers: AppDispatchers,
    private val catalogRemoteDataSource: CatalogRemoteDataSource,
    private val bookItemMapper: BookItemMapper
) : BaseRepository(appDispatchers), CatalogRepository {

    override fun getComics(searchQuery: String) = doPagingFlowRequest(
        pagingSource = BooksPagingSource { offset, limit ->
            catalogRemoteDataSource.getComics(offset, limit, searchQuery)
        }
    ).map { pagingData ->
        pagingData.map {
            bookItemMapper.to(model = it)
        }
    }

    override fun getManga(searchQuery: String) = doPagingFlowRequest(
        pagingSource = BooksPagingSource { offset, limit ->
            catalogRemoteDataSource.getManga(offset, limit, searchQuery)
        }
    ).map { pagingData ->
        pagingData.map {
            bookItemMapper.to(model = it)
        }
    }
}
