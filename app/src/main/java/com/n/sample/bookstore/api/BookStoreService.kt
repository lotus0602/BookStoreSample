package com.n.sample.bookstore.api

import com.n.sample.bookstore.model.BookDetailsDTO
import com.n.sample.bookstore.model.BookListDTO
import retrofit2.http.GET
import retrofit2.http.Path

interface BookStoreService {

    @GET("search/{query}/{page}")
    suspend fun searchBooks(
        @Path("query") query: String,
        @Path("page") page: Int = 0,
    ): BookListDTO

    @GET("new")
    suspend fun getNewReleaseBooks(): BookListDTO

    @GET("books/{isbn13}")
    suspend fun getBookDetails(
        @Path("isbn13") isbn13: String
    ): BookDetailsDTO
}