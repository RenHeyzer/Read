package com.example.read.feature_detail.data.remote.dtos

import com.example.read.utils.constants.Constants
import com.example.read.utils.extensions.await
import com.example.read.utils.mappers.Mappable
import com.parse.ParseObject
import com.parse.ParseQuery

data class InfoDto(
    var id: String? = null,
    var title: String? = null,
    var description: String? = null,
    var coverImage: String? = null,
    var type: String? = null,
    var status: String? = null,
    var rating: Double? = null,
    var genres: List<String>? = null,
    var releaseYear: Int? = null,
    var chapters: List<ChapterDto>? = null,
    var numberOfChapters: Int? = null
) : Mappable

suspend fun ParseObject.toInfoObject(toChapterObject: suspend (ParseQuery<ParseObject>) -> List<ChapterDto>) =
    InfoDto(
        id = objectId,
        title = getString("title"),
        description = getString("description"),
        coverImage = getString("cover_image"),
        type = getString("type"),
        status = getString("status"),
        rating = getDouble("rating"),
        genres = getList("genres"),
        releaseYear = getInt("release_year"),
        chapters = toChapterObject(getRelation<ParseObject>("chapters").query),
        numberOfChapters = getInt("number_of_chapters")
    )

suspend fun InfoDto.toParseObject() = ParseObject(Constants.INFO_CLASS_NAME).apply {
    title?.let { put("title", it) }
    description?.let { put("description", it) }
    coverImage?.let { put("cover_image", it) }
    type?.let { put("type", it) }
    status?.let { put("status", it) }
    rating?.let { put("rating", it) }
    genres?.let { put("genres", it) }
    releaseYear?.let { put("release_year", it) }
    chapters?.let {
        (it.map { chapter ->
            getRelation<ParseObject>("chapters").add(chapter.toParseObject().apply {
                saveInBackground().await()
            })
        })
    }
    numberOfChapters?.let { put("number_of_chapters", it) }
}