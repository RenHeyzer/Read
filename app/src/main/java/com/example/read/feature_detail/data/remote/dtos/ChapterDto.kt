package com.example.read.feature_detail.data.remote.dtos

import com.example.read.utils.DateSerializer
import com.example.read.utils.mappers.Mappable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.util.Date

@Serializable
data class ChapterDto(
    @SerialName("tome")
    val tome: Int? = null,
    @SerialName("nubmer")
    val number: Double? = null,
    @SerialName("title")
    val title: String? = null,
    @SerialName("release_date")
    @Serializable(DateSerializer::class)
    val releaseDate: Date? = null
) : Mappable