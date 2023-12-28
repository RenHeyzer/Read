package com.example.read.feature_home.data.remote.models

import com.example.read.common.mappers.Mappable

data class RecommendationItemDto(
    val id: String? = null,
    val title: String? = null,
    val coverImage: String? = null,
    val posterImage: String? = null
) : Mappable