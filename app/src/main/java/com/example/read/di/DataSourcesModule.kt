package com.example.read.di

import com.example.read.feature_detail.data.remote.sources.BookInfoRemoteDataSource
import com.example.read.feature_detail.data.remote.sources.BookInfoRemoteDataSourceImpl
import com.example.read.feature_home.data.remote.sources.books.BooksRemoteDataSource
import com.example.read.feature_home.data.remote.sources.books.BooksRemoteDataSourceImpl
import com.example.read.feature_home.data.remote.sources.recommendations.RecommendationsRemoteDataSource
import com.example.read.feature_home.data.remote.sources.recommendations.RecommendationsRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DataSourcesModule {

    @Binds
    fun bindBooksRemoteDataSource(remoteDataSourceImpl: BooksRemoteDataSourceImpl): BooksRemoteDataSource

    @Binds
    fun bindBookInfoRemoteDataSource(remoteDataSourceImpl: BookInfoRemoteDataSourceImpl): BookInfoRemoteDataSource

    @Binds
    fun bindRecommendationsRemoteDataSource(remoteDataSourceImpl: RecommendationsRemoteDataSourceImpl): RecommendationsRemoteDataSource
}