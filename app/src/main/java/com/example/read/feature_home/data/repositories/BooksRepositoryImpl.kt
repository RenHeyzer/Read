package com.example.read.feature_home.data.repositories

import androidx.paging.map
import com.example.read.common.data.base.BaseRepository
import com.example.read.common.data.paging.BooksPagingSource
import com.example.read.common.data.remote.models.BookDto
import com.example.read.common.domain.models.Book
import com.example.read.common.mappers.Mapper
import com.example.read.common.utils.AppDispatchers
import com.example.read.feature_home.data.remote.sources.BooksRemoteDataSource
import com.example.read.feature_home.domain.repositories.BooksRepository
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@ViewModelScoped
class BooksRepositoryImpl @Inject constructor(
    private val appDispatchers: AppDispatchers,
    private val booksRemoteDataSource: BooksRemoteDataSource,
    private val bookItemMapper: Mapper<BookDto, Book>
) : BaseRepository(appDispatchers), BooksRepository {
    override fun fetchBooks() = doPagingFlowRequest(
        pagingSource = BooksPagingSource(
            request = { limit, startAfter ->
                booksRemoteDataSource.getBooks(limit, startAfter)
            }
        )
    ).map { items ->
        items.map {
            bookItemMapper.to(it)
        }
    }
}