package com.n.sample.bookstore.api

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.n.sample.bookstore.model.Book
import com.n.sample.bookstore.model.Result

class BookNewReleasePagingSource(
    private val bookStoreRepository: BookStoreRepository
) : PagingSource<Int, Book>() {

    override fun getRefreshKey(state: PagingState<Int, Book>): Int =
        ((state.anchorPosition ?: 0) - state.config.initialLoadSize / 2)
            .coerceAtLeast(DEFAULT_PAGE)

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Book> =
        try {
            val response = bookStoreRepository.getNewReleaseBooks()

            when (response) {
                is Result.Error -> {
                    LoadResult.Error(Exception(response.errorMessage))
                }

                is Result.Success -> {
                    LoadResult.Page(
                        data = response.data.books.map { it.toBook() },
                        prevKey = null,
                        nextKey = null
                    )
                }
            }

        } catch (e: Exception) {
            e.printStackTrace()
            LoadResult.Error(e)
        }
}