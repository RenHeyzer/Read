package com.example.read.utils.mappers

import com.example.read.feature_home.data.remote.dtos.RecommendationItemDto
import com.example.read.feature_home.domain.models.RecommendationItem
import javax.inject.Inject

class RecommendationItemMapper @Inject constructor() :
    Mapper<RecommendationItemDto, RecommendationItem> {

    override fun to(model: RecommendationItemDto) = RecommendationItem(
        id = model.id,
        title = model.title,
        coverImage = model.coverImage,
        posterImage = model.posterImage
    )

    override fun from(model: RecommendationItem) = RecommendationItemDto(
        id = model.id,
        title = model.title,
        coverImage = model.coverImage,
        posterImage = model.posterImage
    )
}