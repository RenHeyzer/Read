package com.example.read.feature_home.data.remote.sources.recommendations

import com.parse.ParseObject

interface RecommendationsRemoteDataSource {

    suspend fun queryRecommendations(limit: Int, skip: Int): List<ParseObject>

    suspend fun querySlides(): List<ParseObject>
}