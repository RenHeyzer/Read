package com.example.read.feature_home.data.remote.sources.recommendations

import com.example.read.feature_home.data.remote.dtos.RecommendationItemDto
import com.example.read.utils.constants.Constants
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.query.Columns
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.decodeFromJsonElement
import javax.inject.Inject

class RecommendationsRemoteDataSourceImpl @Inject constructor(
    private val postgrest: Postgrest
) : RecommendationsRemoteDataSource {

    override suspend fun getRecommendationSlides(): List<RecommendationItemDto> {
        val result = postgrest[Constants.RECOMMENDATION_SLIDES_TABLE_NAME].select(
            columns = Columns.list("id", "poster_image", "info")
        )
        return result.body?.let<JsonElement, List<RecommendationItemDto>> {
            Json { ignoreUnknownKeys = true }.decodeFromJsonElement(
                it
            )
        } ?: emptyList()
    }

    override suspend fun getRecommendations(offset: Long, limit: Long) =
        postgrest[Constants.RECOMMENDATIONS_TABLE_NAME].select {
            range(offset, limit)
        }.decodeList<RecommendationItemDto>()
}