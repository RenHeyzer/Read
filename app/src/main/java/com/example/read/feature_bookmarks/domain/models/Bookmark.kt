package com.example.read.feature_bookmarks.domain.models

import com.example.read.utils.mappers.Mappable

data class Bookmark(
    val id: String,
    val type: String
): Mappable