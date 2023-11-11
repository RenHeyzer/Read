package com.example.read.feature_home.data.remote.dtos

import com.example.read.utils.mapper.Mappable
import com.parse.ParseObject

data class RecommendationItemDto(
    val id: String? = null,
    val title: String? = null,
    val coverImage: String? = null,
    val posterImage: String? = null
): Mappable

fun ParseObject.toRecommendationItemDto() = RecommendationItemDto(
    id = objectId,
    title = getString("title"),
    coverImage = getString("cover_image"),
    posterImage = getString("poster_image")
)