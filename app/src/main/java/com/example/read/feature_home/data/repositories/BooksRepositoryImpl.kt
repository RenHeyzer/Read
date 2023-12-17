package com.example.read.feature_home.data.repositories

import androidx.paging.map
import com.example.read.feature_home.data.paging.BooksPagingSource
import com.example.read.feature_home.data.remote.dtos.BookItemDto
import com.example.read.feature_home.data.remote.sources.BooksRemoteDataSource
import com.example.read.feature_home.domain.models.BookItem
import com.example.read.feature_home.domain.repositories.BooksRepository
import com.example.read.utils.base.BaseRepository
import com.example.read.utils.dispatchers.AppDispatchers
import com.example.read.utils.mappers.Mapper
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@ViewModelScoped
class BooksRepositoryImpl @Inject constructor(
    private val appDispatchers: AppDispatchers,
    private val booksRemoteDataSource: BooksRemoteDataSource,
    private val bookItemMapper: Mapper<BookItemDto, BookItem>
) : BaseRepository(appDispatchers), BooksRepository {
    override fun fetchBooks(searchQuery: String) = doPagingFlowRequest(
        pagingSource = BooksPagingSource(
            booksRemoteDataSource = booksRemoteDataSource,
            searchQuery = searchQuery,
        )
    ).map { items ->
        items.map {
            bookItemMapper.to(it)
        }
    }
}