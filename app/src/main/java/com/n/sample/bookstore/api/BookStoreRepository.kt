package com.n.sample.bookstore.api

import com.n.sample.bookstore.model.BookDetailsDTO
import com.n.sample.bookstore.model.BookListDTO
import com.n.sample.bookstore.model.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
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

    fun getBookDetails(
        isbn13: String
    ): Flow<Result<BookDetailsDTO>> = flow {
        try {
            val result = service.getBookDetails(isbn13)

            if (result.isError()) {
                emit(Result.Error(result.error))
            } else {
                emit(Result.Success(result))
            }
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Result.Error(""))
        }
    }
}