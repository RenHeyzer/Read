package com.example.read.feature_detail.data.repositories

import com.example.read.feature_detail.data.remote.dtos.InfoDto
import com.example.read.feature_detail.data.remote.sources.BookInfoRemoteDataSource
import com.example.read.feature_detail.domain.models.Info
import com.example.read.feature_detail.domain.repositories.BookInfoRepository
import com.example.read.utils.AppDispatchers
import com.example.read.utils.base.BaseRepository
import com.example.read.utils.mapper.Mapper
import javax.inject.Inject

class BookInfoRepositoryImpl @Inject constructor(
    private val appDispatchers: AppDispatchers,
    private val bookInfoRemoteDataSource: BookInfoRemoteDataSource,
    private val infoMapper: Mapper<InfoDto, Info>
) : BaseRepository(appDispatchers), BookInfoRepository {

    override fun getBookInfo(path: String) = doRequest(
        request = {
            bookInfoRemoteDataSource.getBookInfo(path)
        },
        map = {
            if (it != null) infoMapper.to(model = it) else Info()
        }
    )
}