package com.example.read.common.mappers

import com.example.read.common.data.remote.models.BookDto
import com.example.read.common.domain.models.Book
import javax.inject.Inject

class BookItemMapper @Inject constructor(): Mapper<BookDto, Book> {

    override fun to(model: BookDto) = Book(
        id = model.id,
        title = model.title,
        coverImage = model.coverImage,
        releaseYear = model.releaseYear,
        status = model.status,
        rating = model.rating,
        info = model.info
    )

    override fun from(model: Book) = BookDto(
        id = model.id,
        title = model.title,
        coverImage = model.coverImage,
        releaseYear = model.releaseYear,
        status = model.status,
        rating = model.rating,
        info = model.info
    )
}