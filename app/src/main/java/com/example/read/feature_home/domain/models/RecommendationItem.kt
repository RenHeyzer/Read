package com.example.read.feature_home.domain.models

import com.example.read.utils.mappers.Mappable

data class RecommendationItem(
    val id: String? = null,
    val title: String? = null,
    val coverImage: String? = null,
    val posterImage: String? = null
): Mappable