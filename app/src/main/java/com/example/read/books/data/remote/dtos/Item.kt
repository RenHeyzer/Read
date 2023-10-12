package com.example.read.books.data.remote.dtos

import com.example.read.utils.mapper.Mappable
import com.google.firebase.firestore.ServerTimestamp
import java.util.Date

data class ItemDto(
    val id: String? = null,
    val title: String? = null,
    val description: String? = null,
    val genres: List<String>? = null,
    val status: String? = null,
    val type: String? = null,
    val releaseDate: Int? = null,
    val posterImage: String? = null,
    val numberOfChapters: Int? = null,
    val latestChapterNumber: Int? = null,
    val latestChapterTitle: String? = null,
    @ServerTimestamp
    val latestChapterReleaseDate: Date? = null,
): Mappable
