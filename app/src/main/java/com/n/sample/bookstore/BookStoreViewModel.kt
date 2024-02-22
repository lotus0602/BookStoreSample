package com.n.sample.bookstore

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.n.sample.bookstore.api.BookStoreRepository
import com.n.sample.bookstore.model.Book
import com.n.sample.bookstore.model.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookStoreViewModel @Inject constructor(
    private val bookStoreRepository: BookStoreRepository
) : ViewModel() {
    private val _bookList = MutableStateFlow<List<Book>>(emptyList())
    val bookList = _bookList.asStateFlow()

    init {
        viewModelScope.launch {
            bookStoreRepository.getNewReleaseBooks()
                .collect { result ->
                    when(result) {
                        is Result.Error -> {

                        }
                        is Result.Success -> {
                            _bookList.update { result.data }
                        }
                    }
                }
        }
    }

    fun searchBooks(searchText: String, page: Int = 0) {
        viewModelScope.launch {
            bookStoreRepository.searchBooks(searchText, page)
        }
    }
}