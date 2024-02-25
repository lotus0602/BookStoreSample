package com.n.sample.bookstore.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.paging.compose.collectAsLazyPagingItems
import com.n.sample.bookstore.ui.screen.BookDetailsScreen
import com.n.sample.bookstore.ui.screen.BookListScreen
import com.n.sample.bookstore.ui.screen.BookStoreViewModel

object BookStoreDestinations {
    const val BOOK_LIST = "BOOK_LIST"
    const val BOOK_DETAILS = "BOOK_DETAILS"

    object Args {
        const val ISBN_13 = "ISBN_13"
    }
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
            val searchText = viewModel.searchText.collectAsStateWithLifecycle()
            val books = viewModel.books.collectAsLazyPagingItems()

            BookListScreen(
                searchText = searchText.value,
                books = books,
                onSearch = viewModel::onChangeSearchText,
                navigateToDetails = { isbn13 ->
                    navController.navigate(BookStoreDestinations.BOOK_DETAILS + "/$isbn13")
                }
            )
        }
        composable(
            route = BookStoreDestinations.BOOK_DETAILS + "/{${BookStoreDestinations.Args.ISBN_13}}",
            arguments = listOf(
                navArgument(BookStoreDestinations.Args.ISBN_13) { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val viewModel: BookStoreViewModel = hiltViewModel(backStackEntry)
            val data = viewModel.bookDetails.collectAsStateWithLifecycle()

            BookDetailsScreen(data.value)

            LaunchedEffect(Unit) {
                val isbn13 = backStackEntry.arguments?.getString(BookStoreDestinations.Args.ISBN_13)
                viewModel.fetchBookDetails(isbn13)
            }
        }
    }
}