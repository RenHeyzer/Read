package com.example.read.feature_home.data.remote.dtos

import com.example.read.feature_detail.data.remote.dtos.ChapterDto
import com.example.read.utils.mappers.Mappable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BookItemDto(
    @SerialName("id")
    val id: String? = null,
    @SerialName("title")
    val title: String? = null,
    @SerialName("cover_image")
    val coverImage: String? = null,
    @SerialName("release_year")
    val releaseYear: Int? = null,
    @SerialName("status")
    val status: String? = null,
    @SerialName("rating")
    val rating: Double? = null,
    @SerialName("latest_chapter")
    val latestChapter: ChapterDto? = null,
    @SerialName("info")
    val info: String? = null
) : Mappable