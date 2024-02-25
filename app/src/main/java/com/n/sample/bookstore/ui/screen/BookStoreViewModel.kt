package com.n.sample.bookstore.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.n.sample.bookstore.data.repository.BookStoreRepositoryImpl
import com.n.sample.bookstore.domain.model.Book
import com.n.sample.bookstore.domain.model.BookDetails
import com.n.sample.bookstore.domain.model.Result
import com.n.sample.bookstore.domain.usecase.NewReleaseBooksUseCase
import com.n.sample.bookstore.domain.usecase.SearchBooksUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
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
    private val bookStoreRepository: BookStoreRepositoryImpl,
    private val newReleaseBooksUseCase: NewReleaseBooksUseCase,
    private val searchBooksUseCase: SearchBooksUseCase
) : ViewModel() {
    private val _books = MutableStateFlow<PagingData<Book>>(PagingData.empty())
    val books: StateFlow<PagingData<Book>> = _books.asStateFlow()

    private val _bookDetails = MutableStateFlow(BookDetails())
    val bookDetails = _bookDetails.asStateFlow()

    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    init {
        observeSearchText()
        fetchNewReleaseBooks()
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
                .flatMapLatest { query ->
                    searchBooksUseCase(query).cachedIn(viewModelScope)
                }.collectLatest {
                    _books.value = it
                }
        }
    }

    private fun fetchNewReleaseBooks() {
        viewModelScope.launch {
            newReleaseBooksUseCase()
                .cachedIn(viewModelScope)
                .collectLatest {
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
                    _bookDetails.value = result.data
                }
            }
        }
    }
}