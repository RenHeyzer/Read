package com.example.read.utils.mappers

import com.example.read.feature_detail.data.remote.dtos.ChapterDto
import com.example.read.feature_detail.domain.models.Chapter
import javax.inject.Inject

class ChapterMapper @Inject constructor() : Mapper<ChapterDto, Chapter> {

    override fun to(model: ChapterDto) = Chapter(
        tome = model.tome,
        number = model.number,
        title = model.title,
        releaseDate = model.releaseDate
    )

    override fun from(model: Chapter) = ChapterDto(
        tome = model.tome,
        number = model.number,
        title = model.title,
        releaseDate = model.releaseDate
    )
}