package com.n.sample.bookstore.api

import com.n.sample.bookstore.model.BookDetailsDTO
import com.n.sample.bookstore.model.BookListDTO
import com.n.sample.bookstore.model.Result
import javax.inject.Inject

class BookStoreRepository @Inject constructor(
    private val service: BookStoreService
) {

    suspend fun searchBooks(
        searchText: String,
        page: Int = DEFAULT_PAGE
    ): Result<BookListDTO> = try {
        val result = service.searchBooks(searchText, page)

        if (result.isError()) {
            Result.Error(result.error)
        } else {
            Result.Success(result)
        }
    } catch (e: Exception) {
        e.printStackTrace()
        Result.Error(e.message ?: "")
    }

    suspend fun getNewReleaseBooks(): Result<BookListDTO> = try {
        val result = service.getNewReleaseBooks()

        if (result.isError()) {
            Result.Error(result.error)
        } else {
            Result.Success(result)
        }
    } catch (e: Exception) {
        e.printStackTrace()
        Result.Error(e.message ?: "")
    }

    suspend fun getBookDetails(
        isbn13: String
    ): Result<BookDetailsDTO> = try {
        val result = service.getBookDetails(isbn13)

        if (result.isError()) {
            Result.Error(result.error)
        } else {
            Result.Success(result)
        }
    } catch (e: Exception) {
        e.printStackTrace()
        Result.Error("")
    }
}