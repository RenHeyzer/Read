package com.example.read.books.presentation.screens.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.read.books.domain.models.Item
import com.example.read.books.domain.repositories.BookRepository
import com.example.read.utils.TimeDifference
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class BookViewModel @Inject constructor(
    private val bookRepository: BookRepository,
    private val timeDifference: TimeDifference
) : ViewModel() {

    private val _booksState = MutableStateFlow(PagingData.empty<Item>())
    val booksState = _booksState.asStateFlow()

    init {
        fetchBooks()
//        viewModelScope.launch {
//            bookRepository.deleteAllDocuments()
//        }.invokeOnCompletion {
//            Log.e("delete", "success")
//        }
    }

    fun fetchBooks() {
        viewModelScope.launch {
            bookRepository.fetchBooks().cachedIn(viewModelScope).collect {
                _booksState.value = it
            }
        }
    }

    fun getTimeSinceChapterRelease(lastChapterReleaseDate: Date?): String {
        val currentTime = Calendar.getInstance().time
        val timeSinceRelease =
            lastChapterReleaseDate?.let { timeDifference.getTimeDifference(it, currentTime) }
        return timeSinceRelease.toString()
    }
}