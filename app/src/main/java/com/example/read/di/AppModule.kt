package com.example.read.di

import com.example.read.BuildConfig
import com.example.read.utils.constants.Constants
import com.example.read.utils.dispatchers.AppDispatchers
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.annotations.SupabaseInternal
import io.github.jan.supabase.compose.auth.composeAuth
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.gotrue.GoTrue
import io.github.jan.supabase.gotrue.gotrue
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.PropertyConversionMethod
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.realtime.Realtime
import io.github.jan.supabase.realtime.createChannel
import io.github.jan.supabase.realtime.realtime
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.websocket.WebSockets
import io.ktor.client.plugins.websocket.webSocket
import io.ktor.util.InternalAPI
import okhttp3.WebSocket
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideAppDispatchers(): AppDispatchers = AppDispatchers()

    @OptIn(InternalAPI::class)
    @Provides
    @Singleton
    fun provideSupabaseClient() = createSupabaseClient(
        supabaseUrl = BuildConfig.supabaseUrl,
        supabaseKey = BuildConfig.supabaseKey
    ) {
        install(Postgrest) {
            propertyConversionMethod = PropertyConversionMethod.SERIAL_NAME
        }
        install(GoTrue) {
            scheme = "app"
            host = BuildConfig.APPLICATION_ID
            /*  alwaysAutoRefresh = false
              autoLoadFromStorage = false*/
        }
        install(Realtime)
    }

    @Provides
    @Singleton
    fun providePostgrest(supabaseClient: SupabaseClient) = supabaseClient.postgrest

    @Provides
    @Singleton
    fun provideGoTrue(supabaseClient: SupabaseClient) = supabaseClient.gotrue

    @Provides
    @Singleton
    fun provideRealtime(supabaseClient: SupabaseClient) = supabaseClient.realtime
}