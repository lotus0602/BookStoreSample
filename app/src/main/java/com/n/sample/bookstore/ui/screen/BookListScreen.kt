package com.n.sample.bookstore.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun BookListScreen(
    navigateToDetails: () -> Unit,
    modifier: Modifier = Modifier
) {
    BookList(
        navigateToDetails = navigateToDetails,
        modifier = modifier
    )
}

@Preview
@Composable
fun BookListScreenPreview() {
    BookListScreen(navigateToDetails = {})
}

@Composable
fun BookList(
    navigateToDetails: () -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier.fillMaxSize()) {
        items(listOf("item 1", "item 2")) { item ->
            Text(
                text = item,
                color = Color.White,
                modifier = modifier.clickable(onClick = navigateToDetails)
            )
        }
    }
}

@Preview
@Composable
fun BookListPreview() {
    BookList(navigateToDetails = {})
}