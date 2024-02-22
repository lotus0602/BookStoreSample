package com.n.sample.bookstore.model

import kotlinx.serialization.Serializable

@Serializable
data class BookDTO(
    val image: String,
    val isbn13: String,
    val price: String,
    val subtitle: String,
    val title: String,
    val url: String
) : BaseDTO()