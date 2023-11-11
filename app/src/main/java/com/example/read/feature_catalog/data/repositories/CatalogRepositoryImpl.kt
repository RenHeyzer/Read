package com.example.read.feature_catalog.data.repositories

import com.example.read.feature_catalog.data.paging.CatalogPagingSource
import com.example.read.feature_catalog.domain.repositories.CatalogRepository
import com.example.read.feature_home.data.remote.sources.books.BooksRemoteDataSource
import com.example.read.utils.dispatchers.AppDispatchers
import com.example.read.utils.base.BaseRepository
import com.example.read.utils.mappers.BookItemMapper
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class CatalogRepositoryImpl @Inject constructor(
    private val appDispatchers: AppDispatchers,
    private val booksRemoteDataSource: BooksRemoteDataSource,
    private val bookItemMapper: BookItemMapper
) : BaseRepository(appDispatchers), CatalogRepository {

    override fun fetchComics() = doPagingFlowRequest(
        pagingSource = CatalogPagingSource(
            { booksRemoteDataSource.fetchComics(limit = 20, skip = -1, String()) },
            { booksRemoteDataSource.fetchComics(limit = 20, skip = 20, String()) }
        ) {
            bookItemMapper.to(model = it)
        },
    )

    override fun fetchManga() = doPagingFlowRequest(
        pagingSource = CatalogPagingSource(
            { booksRemoteDataSource.fetchManga(limit = 20, skip = -1, String()) },
            { booksRemoteDataSource.fetchManga(limit = 20, skip = 20, String()) }
        ) {
            bookItemMapper.to(model = it)
        },
    )
}