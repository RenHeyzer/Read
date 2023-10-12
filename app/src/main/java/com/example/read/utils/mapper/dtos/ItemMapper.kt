package com.example.read.utils.mapper.dtos

import com.example.read.books.data.remote.dtos.ItemDto
import com.example.read.books.domain.models.Item
import com.example.read.utils.mapper.Mapper
import javax.inject.Inject

class ItemMapper @Inject constructor() : Mapper<ItemDto, Item> {

    override fun toModel(model: ItemDto) = Item(
        id = model.id,
        title = model.title,
        description = model.description,
        genres = model.genres,
        status = model.status,
        type = model.type,
        releaseDate = model.releaseDate,
        posterImage = model.posterImage,
        numberOfChapters = model.numberOfChapters,
        latestChapterNumber = model.latestChapterNumber,
        latestChapterTitle = model.latestChapterTitle,
        latestChapterReleaseDate = model.latestChapterReleaseDate
    )

    override fun fromModel(model: Item) = ItemDto(
        id = model.id,
        title = model.title,
        description = model.description,
        genres = model.genres,
        status = model.status,
        type = model.type,
        releaseDate = model.releaseDate,
        posterImage = model.posterImage,
        numberOfChapters = model.numberOfChapters,
        latestChapterNumber = model.latestChapterNumber,
        latestChapterTitle = model.latestChapterTitle,
        latestChapterReleaseDate = model.latestChapterReleaseDate
    )
}