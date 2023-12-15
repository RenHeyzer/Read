package com.example.read.utils.mappers

import com.example.read.feature_bookmarks.data.remote.dtos.BookmarkDto
import com.example.read.feature_bookmarks.domain.models.Bookmark
import javax.inject.Inject

class BookmarkMapper @Inject constructor() : Mapper<BookmarkDto, Bookmark> {

    override fun to(model: BookmarkDto) = Bookmark(
        id = model.id,
        bookId = model.bookId,
        type = model.type,
        userId = model.userId
    )

    override fun from(model: Bookmark) = BookmarkDto(
        id = model.id,
        bookId = model.bookId,
        type = model.type,
        userId = model.userId
    )
}