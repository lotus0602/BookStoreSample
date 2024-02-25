package com.n.sample.bookstore.data.repository.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.n.sample.bookstore.data.api.DEFAULT_PAGE
import com.n.sample.bookstore.domain.model.Book
import com.n.sample.bookstore.domain.model.Result
import com.n.sample.bookstore.domain.repository.BookStoreRepository

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
                        data = response.data,
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