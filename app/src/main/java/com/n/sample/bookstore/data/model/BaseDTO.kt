package com.n.sample.bookstore.data.model

import kotlinx.serialization.Serializable

@Serializable
abstract class BaseDTO(
    val error: String = ""
) {
    fun isError(): Boolean = (error.isEmpty() || error == "0").not()
}