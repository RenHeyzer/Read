package com.example.read.utils.mappers

import com.example.read.feature_home.data.remote.dtos.BookItemDto
import com.example.read.feature_home.domain.models.BookItem
import javax.inject.Inject

class BookItemMapper @Inject constructor(): Mapper<BookItemDto, BookItem> {

    override fun to(model: BookItemDto) = BookItem(
        id = model.id,
        title = model.title,
        coverImage = model.coverImage,
        releaseYear = model.releaseYear,
        status = model.status,
        rating = model.rating,
        info = model.info
    )

    override fun from(model: BookItem) = BookItemDto(
        id = model.id,
        title = model.title,
        coverImage = model.coverImage,
        releaseYear = model.releaseYear,
        status = model.status,
        rating = model.rating,
        info = model.info
    )
}