package com.example.read.di

import com.example.read.utils.dispatchers.AppDispatchers
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideAppDispatchers(): AppDispatchers = AppDispatchers()

    @Provides
    @Singleton
    fun providedSupabaseClient() = createSupabaseClient(
        supabaseUrl = "https://ckipllpkakxblzzpkjnj.supabase.co",
        supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImNraXBsbHBrYWt4Ymx6enBram5qIiwicm9sZSI6ImFub24iLCJpYXQiOjE2OTkxOTM1MDQsImV4cCI6MjAxNDc2OTUwNH0.geF38px8D4TddS5Tlp3NYpGxqwlKoucRRlEUY3MZqfE"
    ) {
        install(Postgrest)
    }
}