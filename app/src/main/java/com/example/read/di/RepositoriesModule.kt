package com.example.read.di

import com.example.read.books.data.repositories.BookRepositoryImpl
import com.example.read.books.domain.repositories.BookRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface RepositoriesModule {

    @Binds
    fun bindBookRepository(repositoryImpl: BookRepositoryImpl): BookRepository
}