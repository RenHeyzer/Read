package com.example.read.feature_detail.data.remote.dtos

import com.example.read.feature_detail.domain.models.Status
import com.example.read.feature_detail.domain.models.Type
import com.example.read.utils.mappers.Mappable
import com.example.read.utils.serializers.StatusSerializer
import com.example.read.utils.serializers.TypeSerializer
import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class InfoDto(
    @SerialName("id")
    val id: String? = null,
    @SerialName("created_at")
    val createdAt: Instant? = null,
    @SerialName("title")
    val title: String? = null,
    @SerialName("description")
    val description: String? = null,
    @SerialName("cover_image")
    val coverImage: String? = null,
    @SerialName("poster_image")
    val posterImage: String? = null,
    @SerialName("type")
    @Serializable(TypeSerializer::class)
    val type: Type? = null,
    @SerialName("status")
    @Serializable(StatusSerializer::class)
    val status: Status? = null,
    @SerialName("rating")
    val rating: Double? = null,
    @SerialName("genres")
    val genres: List<String>? = null,
    @SerialName("release_year")
    val releaseYear: Int? = null,
    @SerialName("chapters")
    val chapters: List<ChapterDto>? = null,
    @SerialName("number_of_chapters")
    val numberOfChapters: Int? = null
) : Mappable