package com.example.read.feature_home.domain.models

import com.example.read.feature_detail.domain.models.Chapter
import com.example.read.feature_detail.domain.models.Status
import com.example.read.feature_detail.domain.models.Type
import com.example.read.utils.mappers.Mappable

data class BookItem(
    val id: String? = null,
    val createdAt: String? = null,
    val title: String? = null,
    val type: Type? = null,
    val coverImage: String? = null,
    val releaseYear: Int? = null,
    val status: Status? = null,
    val rating: Double? = null,
    val latestChapter: Chapter? = null,
    val info: String? = null
) : Mappable
