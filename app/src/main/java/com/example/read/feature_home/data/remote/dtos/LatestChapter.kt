package com.example.read.feature_home.data.remote.dtos

import com.example.read.feature_detail.data.remote.dtos.ChapterDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LatestChapter(
    @SerialName("latest")
    val latest: ChapterDto? = null
)
