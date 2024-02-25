package com.n.sample.bookstore.data.repository

import com.n.sample.bookstore.data.api.BookStoreService
import com.n.sample.bookstore.domain.model.Book
import com.n.sample.bookstore.domain.model.BookDetails
import com.n.sample.bookstore.domain.model.Result
import com.n.sample.bookstore.domain.repository.BookStoreRepository
import javax.inject.Inject

class BookStoreRepositoryImpl @Inject constructor(
    private val service: BookStoreService
) : BookStoreRepository {

    override suspend fun searchBooks(query: String, page: Int): Result<List<Book>> = try {
        val result = service.searchBooks(query, page)

        if (result.isError()) {
            Result.Error(result.error)
        } else {
            val data = result.books.map { it.toBook() }
            Result.Success(data)
        }
    } catch (e: Exception) {
        e.printStackTrace()
        Result.Error(e.message ?: "")
    }

    override suspend fun getNewReleaseBooks(): Result<List<Book>> = try {
        val result = service.getNewReleaseBooks()

        if (result.isError()) {
            Result.Error(result.error)
        } else {
            val data = result.books.map { it.toBook() }
            Result.Success(data)
        }
    } catch (e: Exception) {
        e.printStackTrace()
        Result.Error(e.message ?: "")
    }

    override suspend fun getBookDetails(
        isbn13: String
    ): Result<BookDetails> = try {
        val result = service.getBookDetails(isbn13)

        if (result.isError()) {
            Result.Error(result.error)
        } else {
            Result.Success(result.toBookDetails())
        }
    } catch (e: Exception) {
        e.printStackTrace()
        Result.Error(e.message ?: "")
    }
}