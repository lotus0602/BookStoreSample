package com.n.sample.bookstore.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.n.sample.bookstore.api.BookPagingSource
import com.n.sample.bookstore.api.BookStoreRepository
import com.n.sample.bookstore.model.Book
import dagger.hilt.android.lifecycle.HiltViewModel
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

    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    init {
        viewModelScope.launch {
            Pager(
                config = PagingConfig(pageSize = 10),
                pagingSourceFactory = {
                    BookPagingSource("", bookStoreRepository)
                }
            ).flow.cachedIn(viewModelScope)
                .collect {
                    _books.value = it
                }
        }

        viewModelScope.launch {
            searchText
                .debounce(1_000L)
                .filter { it.isNotEmpty() }
                .distinctUntilChanged()
                .flatMapLatest {
                    Pager(
                        config = PagingConfig(pageSize = 10, initialLoadSize = 20),
                        pagingSourceFactory = {
                            BookPagingSource(it, bookStoreRepository)
                        }
                    ).flow.cachedIn(viewModelScope)
                }.collectLatest {
                    _books.value = it
                }
        }
    }

    fun onChangeSearchText(text: String) {
        _searchText.value = text
    }
}