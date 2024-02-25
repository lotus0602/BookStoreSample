package com.n.sample.bookstore.domain.usecase

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.n.sample.bookstore.data.repository.paging.BookSearchPagingSource
import com.n.sample.bookstore.domain.model.Book
import com.n.sample.bookstore.domain.repository.BookStoreRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchBooksUseCase @Inject constructor(
    private val repository: BookStoreRepository
) {

    operator fun invoke(query: String): Flow<PagingData<Book>> =
        Pager(
            config = PagingConfig(pageSize = 10, prefetchDistance = 20),
            pagingSourceFactory = { BookSearchPagingSource(query, repository) }
        ).flow
}