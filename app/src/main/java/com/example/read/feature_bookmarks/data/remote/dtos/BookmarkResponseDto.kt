package com.example.read.feature_bookmarks.data.remote.dtos

import com.example.read.feature_home.data.remote.dtos.BookItemDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BookmarkResponseDto(
    @SerialName("books")
    val item: BookItemDto
)