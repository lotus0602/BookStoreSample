package com.n.sample.bookstore.api

import com.n.sample.bookstore.model.Book
import com.n.sample.bookstore.model.BookDetailsDTO
import com.n.sample.bookstore.model.BookListDTO
import com.n.sample.bookstore.model.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class BookStoreRepository @Inject constructor(
    private val service: BookStoreService
) {

    fun searchBooks(
        searchText: String,
        page: Int = 0
    ): Flow<Result<BookListDTO>> = flow {
        try {
            val result = service.searchBooks(searchText, page)

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

    fun getNewReleaseBooks(): Flow<Result<List<Book>>> = flow {
        try {
            val result = service.getNewReleaseBooks()

            if (result.isError()) {
                emit(Result.Error(result.error))
            } else {
                val data = result.books.map { Book(it.image, it.title, it.subtitle, it.price) }
                emit(Result.Success(data))
            }
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Result.Error(""))
        }
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