package com.n.sample.bookstore.di

import com.n.sample.bookstore.data.api.BookStoreService
import com.n.sample.bookstore.data.repository.BookStoreRepositoryImpl
import com.n.sample.bookstore.domain.repository.BookStoreRepository
import com.n.sample.bookstore.domain.usecase.NewReleaseBooksUseCase
import com.n.sample.bookstore.domain.usecase.SearchBooksUseCase
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
    fun provideBookStoreRepository(service: BookStoreService): BookStoreRepository =
        BookStoreRepositoryImpl(service)

    @Singleton
    @Provides
    fun provideNewReleaseBooksUseCase(repository: BookStoreRepository): NewReleaseBooksUseCase =
        NewReleaseBooksUseCase(repository)

    @Singleton
    @Provides
    fun provideSearchBooksUseCase(repository: BookStoreRepository): SearchBooksUseCase =
        SearchBooksUseCase(repository)
}