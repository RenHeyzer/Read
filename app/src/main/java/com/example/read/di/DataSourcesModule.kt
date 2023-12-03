package com.example.read.di

import com.example.read.feature_catalog.data.remote.sources.CatalogRemoteDataSource
import com.example.read.feature_catalog.data.remote.sources.CatalogRemoteDataSourceImpl
import com.example.read.feature_detail.data.remote.sources.BookInfoRemoteDataSource
import com.example.read.feature_detail.data.remote.sources.BookInfoRemoteDataSourceImpl
import com.example.read.feature_home.data.remote.sources.books.BooksRemoteDataSource
import com.example.read.feature_home.data.remote.sources.books.BooksRemoteDataSourceImpl
import com.example.read.feature_home.data.remote.sources.recommendations.RecommendationsRemoteDataSource
import com.example.read.feature_home.data.remote.sources.recommendations.RecommendationsRemoteDataSourceImpl
import com.example.read.feature_login.data.remote.sources.LoginRemoteDataSource
import com.example.read.feature_login.data.remote.sources.LoginRemoteDataSourceImpl
import com.example.read.feature_profile.data.remote.sources.user.UserRemoteDataSource
import com.example.read.feature_profile.data.remote.sources.user.UserRemoteDataSourceImpl
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

    @Binds
    fun bindLoginRemoteDataSource(remoteDataSourceImpl: LoginRemoteDataSourceImpl): LoginRemoteDataSource

    @Binds
    fun bindUserRemoteDataSource(remoteDataSourceImpl: UserRemoteDataSourceImpl): UserRemoteDataSource

    @Binds
    fun bindCatalogRemoteDataSource(remoteDataSourceImpl: CatalogRemoteDataSourceImpl): CatalogRemoteDataSource
}