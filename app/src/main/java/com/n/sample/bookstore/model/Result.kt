package com.n.sample.bookstore.model

sealed class Result<out R> {
    data class Success<out T>(
        val data: T
    ) : Result<T>()

    data class Error(
        val errorMessage: String = ""
    ) : Result<Nothing>()
}