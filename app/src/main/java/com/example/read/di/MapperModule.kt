package com.example.read.di

import com.example.read.feature_detail.data.remote.dtos.ChapterDto
import com.example.read.feature_detail.data.remote.dtos.InfoDto
import com.example.read.feature_detail.domain.models.Chapter
import com.example.read.feature_detail.domain.models.Info
import com.example.read.feature_home.data.remote.dtos.BookItemDto
import com.example.read.feature_home.data.remote.dtos.RecommendationItemDto
import com.example.read.feature_home.domain.models.BookItem
import com.example.read.feature_home.domain.models.RecommendationItem
import com.example.read.utils.mapper.BookItemMapper
import com.example.read.utils.mapper.ChapterMapper
import com.example.read.utils.mapper.InfoMapper
import com.example.read.utils.mapper.Mapper
import com.example.read.utils.mapper.RecommendationItemMapper
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface MapperModule {

    @Binds
    fun bindBookItemMapper(mapper: BookItemMapper): Mapper<BookItemDto, BookItem>

    @Binds
    fun bindInfoMapper(mapper: InfoMapper): Mapper<InfoDto, Info>

    @Binds
    fun bindChapterMapper(mapper: ChapterMapper): Mapper<ChapterDto, Chapter>

    @Binds
    fun bindRecommendationItemMapper(mapper: RecommendationItemMapper): Mapper<RecommendationItemDto, RecommendationItem>
}