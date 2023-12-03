package com.example.read.feature_home.data.remote.dtos

import com.example.read.utils.mappers.Mappable
import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RecommendationItemDto(
    @SerialName("id")
    val id: String? = null,
    @SerialName("created_at")
    val createdAt: Instant? = null,
    @SerialName("title")
    val title: String? = null,
    @SerialName("cover_image")
    val coverImage: String? = null,
    @SerialName("poster_image")
    val posterImage: String? = null,
    @SerialName("info")
    val info: String? = null
): Mappable