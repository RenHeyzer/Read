package com.example.read.books.domain.models

import com.example.read.utils.mapper.Mappable
import java.util.Date

data class Item(
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
    val latestChapterReleaseDate: Date? = null,
): Mappable
