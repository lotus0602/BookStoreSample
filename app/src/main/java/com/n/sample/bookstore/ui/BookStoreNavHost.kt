package com.n.sample.bookstore.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.n.sample.bookstore.BookStoreViewModel
import com.n.sample.bookstore.ui.screen.BookDetailsScreen
import com.n.sample.bookstore.ui.screen.BookListScreen

object BookStoreDestinations {
    const val BOOK_LIST = "BOOK_LIST"
    const val BOOK_DETAILS = "BOOK_DETAILS"
}

@Composable
fun BookStoreNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination: String = BookStoreDestinations.BOOK_LIST
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(
            route = BookStoreDestinations.BOOK_LIST
        ) {
            val viewModel: BookStoreViewModel = hiltViewModel()
            val data = viewModel.bookList.collectAsStateWithLifecycle()
            BookListScreen(
                data = data.value,
                onSearch = { search -> },
                navigateToDetails = {
                    navController.navigate(BookStoreDestinations.BOOK_DETAILS)
                }
            )
        }
        composable(
            route = BookStoreDestinations.BOOK_DETAILS
        ) {
            BookDetailsScreen()
        }
    }
}