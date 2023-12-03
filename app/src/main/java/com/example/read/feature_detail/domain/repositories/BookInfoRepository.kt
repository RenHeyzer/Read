package com.example.read.feature_detail.domain.repositories

import com.example.read.feature_detail.domain.models.Info

interface BookInfoRepository {

    suspend fun getBookInfo(id: String): Info
}