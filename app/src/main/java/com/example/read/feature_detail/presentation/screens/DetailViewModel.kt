package com.example.read.feature_detail.presentation.screens

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.read.feature_detail.domain.models.Info
import com.example.read.feature_detail.domain.repositories.BookInfoRepository
import com.example.read.utils.state_holders.UiState
import com.example.read.utils.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val bookInfoRepository: BookInfoRepository
) : BaseViewModel() {

    private val _infoState = MutableStateFlow<UiState<Info>>(UiState.Loading())
    val infoState = _infoState.asStateFlow()

    private val id: String = checkNotNull(savedStateHandle[INFO_ID_KEY])

    init {
        getBookInfo()
    }

    fun getBookInfo() {
        viewModelScope.launch {
            kotlin.runCatching {
                bookInfoRepository.getBookInfo(id)
            }.onSuccess {
                _infoState.value = UiState.Success(it)
                Log.e("getBookInfo", it.toString())
            }.onFailure {
                it.message?.let { message ->
                    Log.e("getBookInfo", message)
                    _infoState.value = UiState.Error(message)
                }
            }
        }
    }

    companion object {
        const val INFO_ID_KEY = "id"
    }
}