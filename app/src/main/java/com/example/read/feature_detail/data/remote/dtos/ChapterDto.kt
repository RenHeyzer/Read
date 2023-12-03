package com.example.read.feature_detail.data.remote.dtos

import com.example.read.utils.mappers.Mappable
import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ChapterDto(
    @SerialName("tome")
    val tome: Int? = null,
    @SerialName("number")
    val number: Double? = null,
    @SerialName("title")
    val title: String? = null,
    @SerialName("release_date")
    val releaseDate: Instant? = null
) : Mappable