package com.example.read.feature_home.data.remote.sources.recommendations

import com.example.read.utils.constants.Constants
import com.parse.ParseObject
import com.parse.ParseQuery
import com.parse.coroutines.suspendFind
import javax.inject.Inject

class RecommendationsRemoteDataSourceImpl @Inject constructor() : RecommendationsRemoteDataSource {

    override suspend fun queryRecommendations(limit: Int, skip: Int) =
        ParseQuery.getQuery<ParseObject>(Constants.RECOMMENDATIONS_CLASS_NAME).apply {
            this.limit = limit
            this.skip = skip
            this.cachePolicy = ParseQuery.CachePolicy.CACHE_ELSE_NETWORK
        }.suspendFind()

    override suspend fun querySlides() =
        ParseQuery.getQuery<ParseObject>(Constants.RECOMMENDATIONS_SLIDES_CLASS_NAME).suspendFind()
}