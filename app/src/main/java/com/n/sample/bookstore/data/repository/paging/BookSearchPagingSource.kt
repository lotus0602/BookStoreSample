package com.n.sample.bookstore.data.repository.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.n.sample.bookstore.data.api.DEFAULT_PAGE
import com.n.sample.bookstore.domain.model.Book
import com.n.sample.bookstore.domain.model.Result
import com.n.sample.bookstore.domain.repository.BookStoreRepository

class BookSearchPagingSource(
    private val searchText: String,
    private val bookStoreRepository: BookStoreRepository
) : PagingSource<Int, Book>() {

    override fun getRefreshKey(state: PagingState<Int, Book>): Int =
        ((state.anchorPosition ?: 0) - state.config.initialLoadSize / 2)
            .coerceAtLeast(DEFAULT_PAGE)

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Book> =
        try {
            val currentPage = params.key ?: 1
            val response = bookStoreRepository.searchBooks(searchText, currentPage)

            when (response) {
                is Result.Error -> {
                    LoadResult.Error(Exception(response.errorMessage))
                }

                is Result.Success -> {
                    val prevKey = if (currentPage == DEFAULT_PAGE) null else currentPage.minus(1)
                    val nextKey = if (response.data.isEmpty()) null else currentPage.plus(1)

                    LoadResult.Page(
                        data = response.data,
                        prevKey = prevKey,
                        nextKey = nextKey
                    )
                }
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
}