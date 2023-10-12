package com.example.read.books.data.repositories

import com.example.read.books.data.remote.dtos.ItemDto
import com.example.read.books.data.remote.paging.BookPagingSource
import com.example.read.books.data.remote.sources.BookRemoteDataSource
import com.example.read.books.domain.models.Item
import com.example.read.books.domain.repositories.BookRepository
import com.example.read.utils.base.BaseRepository
import com.example.read.utils.mapper.Mapper
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class BookRepositoryImpl @Inject constructor(
    private val booksRemoteDataSource: BookRemoteDataSource,
    private val itemMapper: Mapper<ItemDto, Item>
) :  BaseRepository(), BookRepository {

    override fun fetchBooks() = doPagingFlowRequest(
        pagingSource = BookPagingSource(booksRemoteDataSource) {
            itemMapper.toModel(it)
        }
    )
}