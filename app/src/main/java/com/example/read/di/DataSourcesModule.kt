package com.example.read.di

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
}