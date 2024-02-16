package com.n.sample.bookstore.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun BookDetailsScreen(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = "Book Details",
            color = Color.White
        )
    }
}

@Preview
@Composable
fun BookDetailsScreenPreview() {
    BookDetailsScreen()
}