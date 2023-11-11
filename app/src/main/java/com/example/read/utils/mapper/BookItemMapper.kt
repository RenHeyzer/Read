package com.example.read.utils.mapper

import com.example.read.feature_detail.data.remote.dtos.ChapterDto
import com.example.read.feature_detail.domain.models.Chapter
import com.example.read.feature_home.data.remote.dtos.BookItemDto
import com.example.read.feature_home.domain.models.BookItem
import javax.inject.Inject

class BookItemMapper @Inject constructor(
    private val chapterMapper: Mapper<ChapterDto, Chapter>,
) : Mapper<BookItemDto, BookItem> {

    override fun to(model: BookItemDto) = BookItem(
        id = model.id,
        title = model.title,
        coverImage = model.coverImage,
        releaseYear = model.releaseYear,
        status = model.status,
        rating = model.rating,
        latestChapter = model.latestChapter?.let { chapterMapper.to(model = it) },
        info = model.info
    )

    override fun from(model: BookItem) = BookItemDto(
        id = model.id,
        title = model.title,
        coverImage = model.coverImage,
        releaseYear = model.releaseYear,
        status = model.status,
        rating = model.rating,
        latestChapter = model.latestChapter?.let { chapterMapper.from(model = it) },
        info = model.info
    )
}