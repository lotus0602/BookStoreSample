package com.n.sample.bookstore.model

data class BookDetails(
    val title: String = "",
    val subtitle: String = "",
    val authors: String = "",
    val publisher: String = "",
    val language: String = "",
    val pages: String = "",
    val year: String = "",
    val rating: String = "",
    val desc: String = "",
    val price: String = "",
    val image: String = "",
    val pdf: Map<String, String> = emptyMap()
)
