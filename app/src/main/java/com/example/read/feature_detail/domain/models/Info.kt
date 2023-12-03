package com.example.read.feature_detail.domain.models

import com.example.read.utils.mappers.Mappable
import kotlinx.datetime.Instant

data class Info(
    val id: String? = null,
    val title: String? = null,
    val createdAt: Instant? = null,
    val description: String? = null,
    val coverImage: String? = null,
    val posterImage: String? = null,
    val type: Type? = null,
    val status: Status? = null,
    val rating: Double? = null,
    val genres: List<String>? = null,
    val releaseYear: Int? = null,
    val chapters: List<Chapter>? = null,
    val numberOfChapters: Int? = null
): Mappable
