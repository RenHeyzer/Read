package com.example.read.di

import com.example.read.feature_home.data.repositories.BooksRepositoryImpl
import com.example.read.feature_home.domain.repositories.BooksRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface RepositoriesModule {

    @Binds
    fun bindBooksRepository(repositoryImpl: BooksRepositoryImpl): BooksRepository
}