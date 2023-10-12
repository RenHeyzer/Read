package com.example.read.di

import com.example.read.books.data.remote.dtos.ItemDto
import com.example.read.books.domain.models.Item
import com.example.read.utils.mapper.Mapper
import com.example.read.utils.mapper.dtos.ItemMapper
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface MapperModule {

    @Binds
    fun bindItemMapper(mapper: ItemMapper): Mapper<ItemDto, Item>
}