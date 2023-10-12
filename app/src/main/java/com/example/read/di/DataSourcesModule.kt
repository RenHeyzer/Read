package com.example.read.di

import com.example.read.books.data.remote.sources.BookRemoteDataSource
import com.example.read.books.data.remote.sources.BookRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DataSourcesModule {

    @Binds
    fun bindBookRemoteDataSource(remoteDataSourceImpl: BookRemoteDataSourceImpl): BookRemoteDataSource
}