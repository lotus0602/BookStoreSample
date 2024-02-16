package com.n.sample.bookstore.ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController

@Composable
fun BookStoreApp() {
    MaterialTheme {
        BookStoreNavHost(
            navController = rememberNavController()
        )
    }
}