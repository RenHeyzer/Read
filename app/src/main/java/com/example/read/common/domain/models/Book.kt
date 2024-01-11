package com.example.read.common.domain.models

import com.example.read.common.mappers.Mappable

data class Book(
    val id: String? = null,
    val title: String? = null,
    val coverImage: String? = null,
    val releaseYear: Int? = null,
    val status: String? = null,
    val rating: Double? = null,
    val info: String? = null
) : Mappable
