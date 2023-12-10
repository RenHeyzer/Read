package com.example.read.di

import com.example.read.feature_bookmarks.data.repositories.BookmarksRepositoryImpl
import com.example.read.feature_bookmarks.domain.repositories.BookmarksRepository
import com.example.read.feature_catalog.data.repositories.CatalogRepositoryImpl
import com.example.read.feature_catalog.domain.repositories.CatalogRepository
import com.example.read.feature_detail.data.repositories.BookInfoRepositoryImpl
import com.example.read.feature_detail.domain.repositories.BookInfoRepository
import com.example.read.feature_home.data.repositories.BooksRepositoryImpl
import com.example.read.feature_home.domain.repositories.BooksRepository
import com.example.read.feature_login.data.repositories.LoginRepositoryImpl
import com.example.read.feature_login.domain.repositories.LoginRepository
import com.example.read.feature_profile.data.repositories.ProfileRepositoryImpl
import com.example.read.feature_profile.domain.repositories.ProfileRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface RepositoriesModule {

    @Binds
    fun bindBooksRepository(repositoryImpl: BooksRepositoryImpl): BooksRepository

    @Binds
    fun bindCatalogRepository(repositoryImpl: CatalogRepositoryImpl): CatalogRepository

    @Binds
    fun bindBookInfoRepository(repositoryImpl: BookInfoRepositoryImpl): BookInfoRepository
    
    @Binds
    fun bindLoginRepository(repositoryImpl: LoginRepositoryImpl): LoginRepository

    @Binds
    fun bindUserRepository(repositoryImpl: ProfileRepositoryImpl): ProfileRepository

    @Binds
    fun bindBookmarksRepository(repositoryImpl: BookmarksRepositoryImpl): BookmarksRepository
}