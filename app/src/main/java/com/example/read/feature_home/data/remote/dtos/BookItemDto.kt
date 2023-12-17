package com.example.read.feature_home.data.remote.dtos

import com.example.read.utils.mappers.Mappable

data class BookItemDto(
    val id: String? = null,
    val title: String? = null,
    val coverImage: String? = null,
    val releaseYear: Int? = null,
    val status: String? = null,
    val rating: Double? = null,
    val info: String? = null
) : Mappable