package com.example.read.feature_detail.data.remote.sources

import com.example.read.feature_detail.data.remote.dtos.InfoDto

interface BookInfoRemoteDataSource {

    suspend fun getBookInfo(id: String): InfoDto
}