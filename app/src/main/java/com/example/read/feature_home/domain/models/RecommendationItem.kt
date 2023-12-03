package com.example.read.feature_home.domain.models

import com.example.read.utils.mappers.Mappable
import kotlinx.datetime.Instant

data class RecommendationItem(
    val id: String? = null,
    val title: String? = null,
    val createdAt: Instant? = null,
    val coverImage: String? = null,
    val posterImage: String? = null,
    val info: String? = null
): Mappable