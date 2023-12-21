package com.example.read.di

import com.example.read.feature_home.data.remote.dtos.BookItemDto
import com.example.read.feature_home.data.remote.dtos.RecommendationItemDto
import com.example.read.feature_home.domain.models.BookItem
import com.example.read.feature_home.domain.models.RecommendationItem
import com.example.read.utils.mappers.BookItemMapper
import com.example.read.utils.mappers.Mapper
import com.example.read.utils.mappers.RecommendationItemMapper
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface MapperModule {

    @Binds
    fun bindBookItemMapper(mapper: BookItemMapper): Mapper<BookItemDto, BookItem>

    @Binds
    fun bindRecommendationItemMapper(mapper: RecommendationItemMapper): Mapper<RecommendationItemDto, RecommendationItem>
}