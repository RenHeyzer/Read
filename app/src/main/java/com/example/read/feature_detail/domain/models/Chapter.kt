package com.example.read.feature_detail.domain.models

import com.example.read.utils.mappers.Mappable
import java.util.Date

data class Chapter(
    val tome: Int? = null,
    val number: Double? = null,
    val title: String? = null,
    val releaseDate: Date? = null,
    val timeSinceRelease: String? = null
) : Mappable
