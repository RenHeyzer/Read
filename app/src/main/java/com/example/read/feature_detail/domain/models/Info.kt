package com.example.read.feature_detail.domain.models

import com.example.read.utils.mappers.Mappable

data class Info(
    val id: String? = null,
    val title: String? = null,
    val description: String? = null,
    val coverImage: String? = null,
    val type: String? = null,
    val status: String? = null,
    val rating: Double? = null,
    val genres: List<String>? = null,
    val releaseYear: Int? = null,
    val chapters: List<Chapter>? = null,
    val numberOfChapters: Int? = null
): Mappable
