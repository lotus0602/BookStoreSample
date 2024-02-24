package com.n.sample.bookstore.model

import kotlinx.serialization.Serializable

@Serializable
data class BookDetailsDTO(
    val title: String,
    val subtitle: String,
    val authors: String,
    val publisher: String,
    val language: String,
    val isbn10: String,
    val isbn13: String,
    val pages: String,
    val year: String,
    val rating: String,
    val desc: String,
    val price: String,
    val image: String,
    val url: String,
    val pdf: Map<String, String> = emptyMap()
) : BaseDTO() {

    fun toBookDetails(): BookDetails =
        BookDetails(
            title = title,
            subtitle = subtitle,
            authors = authors,
            publisher = publisher,
            language = language,
            pages = pages,
            year = year,
            rating = rating,
            desc = desc,
            price = price,
            image = image,
            pdf = pdf
        )
}
