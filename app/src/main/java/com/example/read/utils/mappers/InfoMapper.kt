package com.example.read.utils.mappers

import com.example.read.feature_detail.data.remote.dtos.ChapterDto
import com.example.read.feature_detail.data.remote.dtos.InfoDto
import com.example.read.feature_detail.domain.models.Chapter
import com.example.read.feature_detail.domain.models.Info
import javax.inject.Inject

class InfoMapper @Inject constructor(
    private val chapterMapper: Mapper<ChapterDto, Chapter>
) : Mapper<InfoDto, Info> {

    override fun to(model: InfoDto) = Info(
        id = model.id,
        createdAt = model.createdAt,
        title = model.title,
        description = model.description,
        coverImage = model.coverImage,
        type = model.type,
        status = model.status,
        rating = model.rating,
        genres = model.genres,
        releaseYear = model.releaseYear,
        chapters = model.chapters?.map {
            chapterMapper.to(it)
        },
        numberOfChapters = model.numberOfChapters
    )

    override fun from(model: Info) = InfoDto(
        id = model.id,
        createdAt = model.createdAt,
        title = model.title,
        description = model.description,
        coverImage = model.coverImage,
        type = model.type,
        status = model.status,
        rating = model.rating,
        genres = model.genres,
        releaseYear = model.releaseYear,
        chapters = model.chapters?.map {
            chapterMapper.from(it)
        },
        numberOfChapters = model.numberOfChapters
    )
}