package com.n.sample.bookstore.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.n.sample.bookstore.api.BookNewReleasePagingSource
import com.n.sample.bookstore.api.BookSearchPagingSource
import com.n.sample.bookstore.api.BookStoreRepository
import com.n.sample.bookstore.model.Book
import com.n.sample.bookstore.model.BookDetails
import com.n.sample.bookstore.model.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookStoreViewModel @Inject constructor(
    private val bookStoreRepository: BookStoreRepository
) : ViewModel() {
    private val _books = MutableStateFlow<PagingData<Book>>(PagingData.empty())
    val books: StateFlow<PagingData<Book>> = _books.asStateFlow()

    private val _bookDetails = MutableStateFlow(BookDetails())
    val bookDetails = _bookDetails.asStateFlow()

    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    init {
        observeSearchText()
        fetchNewReleaseBoos()
    }

    fun onChangeSearchText(text: String) {
        _searchText.value = text
    }

    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    private fun observeSearchText() {
        viewModelScope.launch {
            searchText
                .debounce(1_000L)
                .filter { it.isNotEmpty() }
                .distinctUntilChanged()
                .flatMapLatest {
                    getSearchBoosPagingData(it)
                }.collectLatest {
                    _books.value = it
                }
        }
    }

    private fun getSearchBoosPagingData(text: String): Flow<PagingData<Book>> =
        Pager(
            config = PagingConfig(pageSize = 10, initialLoadSize = 20),
            pagingSourceFactory = {
                BookSearchPagingSource(text, bookStoreRepository)
            }
        ).flow.cachedIn(viewModelScope)

    private fun fetchNewReleaseBoos() {
        viewModelScope.launch {
            Pager(
                config = PagingConfig(pageSize = 20),
                pagingSourceFactory = { BookNewReleasePagingSource(bookStoreRepository) }
            ).flow.cachedIn(viewModelScope)
                .collect {
                    _books.value = it
                }
        }
    }

    fun fetchBookDetails(isbn13: String?) {
        viewModelScope.launch {

            if (isbn13.isNullOrEmpty()) return@launch

            val result = bookStoreRepository.getBookDetails(isbn13)
            when (result) {
                is Result.Error -> {

                }

                is Result.Success -> {
                    _bookDetails.value = result.data.toBookDetails()
                }
            }
        }
    }
}