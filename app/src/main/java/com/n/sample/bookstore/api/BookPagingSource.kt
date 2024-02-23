package com.n.sample.bookstore.api

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.n.sample.bookstore.model.Book
import com.n.sample.bookstore.model.Result

class BookPagingSource(
    private val searchText: String,
    private val bookStoreRepository: BookStoreRepository
) : PagingSource<Int, Book>() {

    override fun getRefreshKey(state: PagingState<Int, Book>): Int =
        ((state.anchorPosition ?: 0) - state.config.initialLoadSize / 2)
            .coerceAtLeast(0)

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Book> =
        try {
            val currentPage = params.key ?: 1
            val response =
                if (searchText.isEmpty()) {
                    bookStoreRepository.getNewReleaseBooks()
                } else {
                    bookStoreRepository.searchBooks(searchText, currentPage)
                }

            when (response) {
                is Result.Error -> {
                    LoadResult.Error(Exception(response.errorMessage))
                }

                is Result.Success -> {
                    if (searchText.isEmpty()) {
                        LoadResult.Page(
                            data = response.data.books.map { it.toBook() },
                            prevKey = null,
                            nextKey = null
                        )
                    } else {
                        val prevKey = if (currentPage == DEFAULT_PAGE) null else currentPage - 1
                        val nextKey =
                            if (response.data.books.isEmpty()) null else response.data.page + 1
                        LoadResult.Page(
                            data = response.data.books.map { it.toBook() },
                            prevKey = prevKey,
                            nextKey = nextKey
                        )
                    }
                }
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
}