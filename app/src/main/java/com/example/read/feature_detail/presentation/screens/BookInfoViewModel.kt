package com.example.read.feature_detail.presentation.screens

import androidx.lifecycle.SavedStateHandle
import com.example.read.feature_detail.domain.models.Info
import com.example.read.feature_detail.domain.repositories.BookInfoRepository
import com.example.read.utils.state_holders.UiState
import com.example.read.utils.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class BookInfoViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val bookInfoRepository: BookInfoRepository
) : BaseViewModel() {

    private val _infoState = MutableStateFlow<UiState<Info>>(UiState.Loading())
    val infoState = _infoState.asStateFlow()

    private val path: String = checkNotNull(savedStateHandle[PATH_KEY])

    init {
        collectBookInfo()
    }

    fun collectBookInfo() {
        val correctPath = path.replace("_", "/")
        bookInfoRepository.getBookInfo(correctPath).collectFlowAsState(_infoState)
    }

    companion object {
        const val PATH_KEY = "path"
    }
}