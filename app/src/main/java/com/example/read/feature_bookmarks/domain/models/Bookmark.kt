package com.example.read.feature_bookmarks.domain.models

import com.example.read.utils.mappers.Mappable
import java.util.UUID

data class Bookmark(
    val id: String = UUID.randomUUID().toString(),
    val bookId: String,
    val type: String,
    val userId: String
): Mappable