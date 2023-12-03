package com.example.read.feature_bookmarks.data.remote.dtos

import com.example.read.utils.mappers.Mappable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BookmarkDto(
    @SerialName("id")
    val id: String,
    @SerialName("bookmark_type")
    val type: String
): Mappable