package com.n.sample.bookstore.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Singleton
    @Provides
    fun provideClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor()
            .apply {
                setLevel(HttpLoggingInterceptor.Level.BODY)
            }

        return OkHttpClient.Builder()
            .apply {
                addNetworkInterceptor(logging)
            }.build()
    }

    @Singleton
    @Provides
    fun provideKotlinSerializationConverter(): Converter.Factory {
        val contentType = "application/json".toMediaType()
        val json = Json {
            coerceInputValues = true
            encodeDefaults = true
            ignoreUnknownKeys = true
            isLenient = true
        }
        return json.asConverterFactory(contentType)
    }

    @Singleton
    @Provides
    fun provideBookStoreApiRetrofit(
        client: OkHttpClient,
        kotlinSerializationConverter: Converter.Factory
    ): Retrofit = Retrofit.Builder()
        .apply {
            baseUrl("https://api.itbook.store/1.0/")
            client(client)
            addConverterFactory(kotlinSerializationConverter)
        }.build()
}