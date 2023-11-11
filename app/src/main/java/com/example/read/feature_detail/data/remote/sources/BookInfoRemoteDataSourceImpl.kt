package com.example.read.feature_detail.data.remote.sources

import com.example.read.feature_detail.data.remote.dtos.InfoDto
import javax.inject.Inject

class BookInfoRemoteDataSourceImpl @Inject constructor() : BookInfoRemoteDataSource {

    override suspend fun getBookInfo(path: String) = InfoDto()
}