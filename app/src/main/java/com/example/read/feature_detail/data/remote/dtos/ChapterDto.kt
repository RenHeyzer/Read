package com.example.read.feature_detail.data.remote.dtos

import com.example.read.utils.constants.Constants
import com.example.read.utils.mappers.Mappable
import com.parse.ParseObject
import java.util.Date

data class ChapterDto(
    var tome: Int? = null,
    var number: Double? = null,
    var title: String? = null,
    var releaseDate: Date? = null
) : Mappable

fun ParseObject.toChapterObject() = ChapterDto(
    tome = getInt("tome"),
    number = getDouble("number"),
    title = getString("title"),
    releaseDate = getDate("release_date")
)

fun ChapterDto.toParseObject() = ParseObject(Constants.CHAPTERS_CLASS_NAME).apply {
    tome?.let { put("tome", it) }
    number?.let { put("number", it) }
    title?.let { put("title", it) }
    releaseDate?.let { put("release_date", it) }
}