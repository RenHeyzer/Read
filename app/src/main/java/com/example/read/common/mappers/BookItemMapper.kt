package com.example.read.common.mappers

import com.example.read.common.data.remote.models.BookDto
import com.example.read.common.domain.models.BookEntity
import javax.inject.Inject

class BookItemMapper @Inject constructor(): Mapper<BookDto, BookEntity> {

    override fun to(model: BookDto) = BookEntity(
        id = model.id,
        title = model.title,
        coverImage = model.coverImage,
        releaseYear = model.releaseYear,
        status = model.status,
        rating = model.rating,
        info = model.info
    )

    override fun from(model: BookEntity) = BookDto(
        id = model.id,
        title = model.title,
        coverImage = model.coverImage,
        releaseYear = model.releaseYear,
        status = model.status,
        rating = model.rating,
        info = model.info
    )
}