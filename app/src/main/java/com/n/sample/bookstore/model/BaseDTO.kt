package com.n.sample.bookstore.model

import kotlinx.serialization.Serializable

@Serializable
abstract class BaseDTO(
    val error: String = ""
) {
    fun isError(): Boolean = (error.isEmpty() || error == "0").not()
}