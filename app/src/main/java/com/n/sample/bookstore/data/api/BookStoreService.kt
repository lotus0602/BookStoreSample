package com.n.sample.bookstore.data.api

import com.n.sample.bookstore.data.model.BookDetailsDTO
import com.n.sample.bookstore.data.model.BookListDTO
import retrofit2.http.GET
import retrofit2.http.Path

interface BookStoreService {

    @GET("search/{query}/{page}")
    suspend fun searchBooks(
        @Path("query") query: String,
        @Path("page") page: Int = DEFAULT_PAGE,
    ): BookListDTO

    @GET("new")
    suspend fun getNewReleaseBooks(): BookListDTO

    @GET("books/{isbn13}")
    suspend fun getBookDetails(
        @Path("isbn13") isbn13: String
    ): BookDetailsDTO
}