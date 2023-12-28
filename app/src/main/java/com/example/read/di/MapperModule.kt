package com.example.read.di

import com.example.read.common.data.remote.models.BookDto
import com.example.read.feature_home.data.remote.models.RecommendationItemDto
import com.example.read.common.domain.models.BookEntity
import com.example.read.feature_home.domain.models.RecommendationItem
import com.example.read.common.mappers.BookItemMapper
import com.example.read.common.mappers.Mapper
import com.example.read.common.mappers.RecommendationItemMapper
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface MapperModule {

    @Binds
    fun bindBookItemMapper(mapper: BookItemMapper): Mapper<BookDto, BookEntity>

    @Binds
    fun bindRecommendationItemMapper(mapper: RecommendationItemMapper): Mapper<RecommendationItemDto, RecommendationItem>
}