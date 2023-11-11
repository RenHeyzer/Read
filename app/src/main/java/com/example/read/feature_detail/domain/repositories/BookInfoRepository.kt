package com.example.read.feature_detail.domain.repositories

import com.example.read.feature_detail.domain.models.Info
import com.example.read.utils.Either
import kotlinx.coroutines.flow.Flow

interface BookInfoRepository {

    fun getBookInfo(path: String): Flow<Either<String, Info>>
}