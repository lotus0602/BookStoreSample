package com.n.sample.bookstore.domain.repository

import com.n.sample.bookstore.domain.model.Book
import com.n.sample.bookstore.domain.model.BookDetails
import com.n.sample.bookstore.domain.model.Result

interface BookStoreRepository {
    suspend fun searchBooks(
        query: String,
        page: Int
    ): Result<List<Book>>

    suspend fun getNewReleaseBooks(): Result<List<Book>>

    suspend fun getBookDetails(
        isbn13: String
    ): Result<BookDetails>
}