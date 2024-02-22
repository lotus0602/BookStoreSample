package com.n.sample.bookstore.di

import com.n.sample.bookstore.api.BookStoreRepository
import com.n.sample.bookstore.api.BookStoreService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataSourceModule {

    @Singleton
    @Provides
    fun provideBookStoreService(retrofit: Retrofit): BookStoreService =
        retrofit.create(BookStoreService::class.java)

    @Singleton
    @Provides
    fun provideBookStoreRepository(service: BookStoreService) =
        BookStoreRepository(service)
}