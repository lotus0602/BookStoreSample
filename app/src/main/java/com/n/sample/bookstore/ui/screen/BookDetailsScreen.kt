package com.n.sample.bookstore.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.n.sample.bookstore.domain.model.BookDetails

@Composable
fun BookDetailsScreen(
    data: BookDetails,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        AsyncImage(
            model = data.image,
            contentDescription = null,
            modifier = modifier
                .fillMaxWidth()
                .aspectRatio(2f / 1f)
        )
        Spacer(modifier = modifier.height(8.dp))

        Text(
            text = data.title,
            color = Color.White,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = modifier.padding(horizontal = 20.dp)
        )
        Text(
            text = data.subtitle,
            color = Color.White,
            fontSize = 16.sp,
            modifier = modifier.padding(horizontal = 20.dp)
        )
        Text(
            text = data.price,
            color = Color.Red,
            fontSize = 16.sp,
            modifier = modifier
                .align(Alignment.End)
                .padding(horizontal = 20.dp)
        )

        BookInfoRow(title = "Desc", contents = data.desc)
        BookInfoRow(title = "Authors", contents = data.authors)
        BookInfoRow(title = "Language", contents = data.language)
        BookInfoRow(title = "Pages", contents = data.pages)
        BookInfoRow(title = "Year", contents = data.year)
        BookInfoRow(title = "Rating", contents = data.rating)
        BookInfoRow(title = "Publisher", contents = data.publisher)

        if (data.pdf.isNotEmpty()) {
            val uriHandler = LocalUriHandler.current
            val annotatedString = buildAnnotatedString {
                data.pdf.forEach { (key, value) ->
                    pushStringAnnotation(tag = key, annotation = value)
                    withStyle(
                        style = SpanStyle(color = Color.Blue, fontWeight = FontWeight.ExtraBold)
                    ) {
                        append(key)
                        append("\n")
                    }
                    pop()
                }
            }
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 10.dp)
            ) {
                Text(
                    text = "Pdf",
                    color = Color.White,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.ExtraBold,
                    modifier = modifier.weight(1f)
                )
                Spacer(modifier = modifier.width(10.dp))

                ClickableText(
                    text = annotatedString,
                    onClick = { offset ->
                        annotatedString.getStringAnnotations(offset, offset)
                            .firstOrNull()?.let {
                                uriHandler.openUri(it.item)
                            }
                    },
                    modifier = modifier.weight(4f)
                )
            }
        }
    }
}

@Preview
@Composable
fun BookDetailsScreenPreview() {
    BookDetailsScreen(
        BookDetails(
            title = "title",
            subtitle = "subtitle",
            authors = "authors",
            publisher = "publisher",
            language = "language",
            pages = "pages",
            year = "year",
            rating = "rating",
            desc = "desc",
            price = "price",
            image = "image",
        )
    )
}

@Composable
fun BookInfoRow(
    title: String,
    contents: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 10.dp)
    ) {
        Text(
            text = title,
            color = Color.White,
            fontSize = 14.sp,
            fontWeight = FontWeight.ExtraBold,
            modifier = modifier.weight(1f)
        )
        Spacer(modifier = modifier.width(10.dp))

        Text(
            text = contents,
            color = Color.Gray,
            fontSize = 14.sp,
            modifier = modifier.weight(4f)
        )
    }
}