package com.example.read.feature_home.data.remote.sources.recommendations

import com.example.read.feature_home.data.remote.dtos.RecommendationItemDto

interface RecommendationsRemoteDataSource {

    suspend fun getRecommendationSlides(): List<RecommendationItemDto>

    suspend fun getRecommendations(offset: Long, limit: Long): List<RecommendationItemDto>
}