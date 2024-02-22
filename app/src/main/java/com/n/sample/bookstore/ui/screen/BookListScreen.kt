package com.n.sample.bookstore.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.n.sample.bookstore.model.Book

sealed interface ListType {
    object List : ListType
    object Grid : ListType
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookListScreen(
    data: List<Book>,
    onSearch: (search: String) -> Unit,
    navigateToDetails: () -> Unit,
    modifier: Modifier = Modifier
) {
    var searchText by remember { mutableStateOf("") }
    var listType by remember { mutableStateOf<ListType>(ListType.List) }

    Column {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                value = searchText,
                onValueChange = {
                    searchText = it
                    onSearch(it)
                },
                modifier = modifier.weight(1f)
            )
            Spacer(modifier = modifier.width(10.dp))

            Button(onClick = {
                listType = when (listType) {
                    ListType.List -> {
                        ListType.Grid
                    }

                    ListType.Grid -> {
                        ListType.List
                    }
                }
            }) {
                val icon = when (listType) {
                    ListType.List -> Icons.Default.MoreVert
                    ListType.Grid -> Icons.Default.List
                }
                Icon(imageVector = icon, contentDescription = null)
            }
        }

        BookList(
            data = data,
            listType = listType,
            navigateToDetails = navigateToDetails,
            modifier = modifier
        )
    }
}

@Preview
@Composable
fun BookListScreenPreview() {
    BookListScreen(
        data = listOf(
            Book("", "title", "sub title", "price"),
            Book("", "title", "sub title", "price"),
        ),
        onSearch = { },
        navigateToDetails = {}
    )
}

@Composable
fun BookList(
    data: List<Book>,
    listType: ListType,
    navigateToDetails: () -> Unit,
    modifier: Modifier = Modifier
) {
    when (listType) {
        ListType.List -> {
            LazyColumn(modifier.fillMaxSize()) {
                items(data) { item ->
                    BookListItem(data = item, onClickItem = navigateToDetails)
                }
            }
        }

        ListType.Grid -> {
            LazyVerticalGrid(columns = GridCells.Fixed(3)) {
                items(data) { item ->
                    BookGridItem(data = item, onClickItem = navigateToDetails)
                }
            }
        }
    }
}

@Preview
@Composable
fun BookListPreview() {
    BookList(
        data = listOf(
            Book("", "title", "sub title", "price"),
            Book("", "title", "sub title", "price"),
        ),
        listType = ListType.List,
        navigateToDetails = {}
    )
}

@Composable
fun BookListItem(
    data: Book,
    onClickItem: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
            .padding(8.dp)
            .clickable(onClick = onClickItem::invoke)
    ) {
        AsyncImage(
            model = data.image,
            contentDescription = null,
            modifier = modifier
                .height(height = 80.dp)
                .aspectRatio(1f / 1f)
        )
        Spacer(modifier = modifier.width(8.dp))
        Column(
            modifier = modifier
                .fillMaxHeight()
                .weight(1f, true),
            verticalArrangement = Arrangement.SpaceAround
        ) {
            Text(
                text = data.title,
                color = Color.White,
            )
            Text(
                text = data.subTitle,
                color = Color.White,
            )
        }
        Spacer(modifier = modifier.width(8.dp))
        Text(
            text = data.price,
            color = Color.White,
            modifier = modifier.align(Alignment.Bottom)
        )
    }
}

@Preview
@Composable
fun BookListItemPreview() {
    BookListItem(
        data = Book("", "title", "sub title", "price"),
        onClickItem = {}
    )
}

@Composable
fun BookGridItem(
    data: Book,
    onClickItem: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(8.dp)
            .clickable(onClick = onClickItem::invoke)
    ) {
        AsyncImage(
            model = data.image,
            contentDescription = null,
            modifier = modifier
                .aspectRatio(1f / 1f)
        )
        Spacer(modifier = modifier.width(8.dp))
        Text(
            text = data.title,
            color = Color.White,
        )
        Text(
            text = data.subTitle,
            color = Color.White,
        )
        Text(
            text = data.price,
            color = Color.White,
        )
    }
}

@Preview
@Composable
fun BookGridItemPreview() {
    BookGridItem(
        data = Book("", "title", "sub title", "price"),
        onClickItem = {}
    )
}