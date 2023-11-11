package com.example.read.feature_home.data.remote.dtos

import com.example.read.feature_detail.data.remote.dtos.ChapterDto
import com.example.read.feature_detail.data.remote.dtos.toChapterObject
import com.example.read.utils.mappers.Mappable
import com.parse.ParseObject

data class BookItemDto(
    val id: String? = null,
    val title: String? = null,
    val coverImage: String? = null,
    val releaseYear: Int? = null,
    val status: String? = null,
    val rating: Double? = null,
    val latestChapter: ChapterDto? = null,
    val info: String? = null
) : Mappable

fun ParseObject.toBookItemDto() =
    BookItemDto(
        id = objectId,
        title = getString("title"),
        coverImage = getString("cover_image"),
        releaseYear = getInt("release_year"),
        status = getString("status"),
        rating = getDouble("rating"),
        latestChapter = getParseObject("latest_chapter")?.fetchIfNeeded<ParseObject>()
            ?.toChapterObject(),
        info = getString("info")
    )

fun BookItemDto.toParseObject(className: String, latestChapter: ParseObject) = ParseObject(className).apply {
    title?.let { put("title", it) }
    coverImage?.let { put("cover_image", it) }
    releaseYear?.let { put("release_year", it) }
    status?.let { put("status", it) }
    rating?.let { put("rating", it) }
    put("latest_chapter", latestChapter)
    id?.let { put("info", it) }
}