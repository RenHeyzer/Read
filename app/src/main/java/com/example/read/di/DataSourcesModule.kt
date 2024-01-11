package com.example.read.di

import com.example.read.feature_auth.data.remote.sources.AuthRemoteDataSource
import com.example.read.feature_auth.data.remote.sources.AuthRemoteDataSourceImpl
import com.example.read.feature_catalog.data.remote.sources.CatalogRemoteDataSource
import com.example.read.feature_catalog.data.remote.sources.CatalogRemoteDataSourceImpl
import com.example.read.feature_home.data.remote.sources.BooksRemoteDataSource
import com.example.read.feature_home.data.remote.sources.BooksRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface DataSourcesModule {

    @Binds
    fun bindBookRemoteDataSource(remoteDataSourceImpl: BooksRemoteDataSourceImpl): BooksRemoteDataSource

    @Binds
    fun bindCatalogRemoteDataSource(remoteDataSourceImpl: CatalogRemoteDataSourceImpl): CatalogRemoteDataSource

    @Binds
    fun bindSignUpRemoteDataSource(remoteDataSourceImpl: AuthRemoteDataSourceImpl): AuthRemoteDataSource
}