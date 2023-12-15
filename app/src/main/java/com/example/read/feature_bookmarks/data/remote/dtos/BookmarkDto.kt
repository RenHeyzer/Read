package com.example.read.feature_bookmarks.data.remote.dtos

import com.example.read.utils.mappers.Mappable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BookmarkDto(
    @SerialName("id")
    val id: String,
    @SerialName("book_id")
    val bookId: String,
    @SerialName("bookmark_type")
    val type: String,
    @SerialName("user_id")
    val userId: String
): Mappable