package com.example.read.feature_home.data.remote.dtos

import com.example.read.feature_detail.domain.models.Status
import com.example.read.feature_detail.domain.models.Type
import com.example.read.utils.mappers.Mappable
import com.example.read.utils.serializers.StatusSerializer
import com.example.read.utils.serializers.TypeSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BookItemDto(
    @SerialName("id")
    val id: String? = null,
    @SerialName("created_at")
    val createdAt: String? = null,
    @SerialName("title")
    val title: String? = null,
    @SerialName("type")
    @Serializable(TypeSerializer::class)
    val type: Type? = null,
    @SerialName("cover_image")
    val coverImage: String? = null,
    @SerialName("release_year")
    val releaseYear: Int? = null,
    @SerialName("status")
    @Serializable(StatusSerializer::class)
    val status: Status? = null,
    @SerialName("rating")
    val rating: Double? = null,
    @SerialName("latest_chapter")
    val latestChapter: LatestChapter? = null,
    @SerialName("info")
    val info: String? = null
) : Mappable