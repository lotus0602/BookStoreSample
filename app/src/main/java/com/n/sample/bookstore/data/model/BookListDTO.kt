package com.n.sample.bookstore.data.model

import kotlinx.serialization.Serializable

@Serializable
data class BookListDTO(
    val total: Int = 0,
    val page: Int = 0,
    val books: List<BookDTO> = emptyList()
): BaseDTO()
