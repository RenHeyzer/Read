package com.example.read.common.presentation.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.read.common.domain.utils.Either
import com.example.read.common.presentation.utils.UiState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel : ViewModel() {

    protected open fun <T : Any> Flow<PagingData<T>>.collectFlowAsPaging(
        state: MutableStateFlow<PagingData<T>>
    ) {
        viewModelScope.launch {
            this@collectFlowAsPaging.cachedIn(viewModelScope).collect {
                state.value = it
            }
        }
    }

    protected open fun <T> Flow<Either<String, T>>.collectFlowAsState(
        state: MutableStateFlow<UiState<T>>,
    ) {
        viewModelScope.launch {
            this@collectFlowAsState.collect {
                when (it) {
                    is Either.Left -> it.message?.let { message ->
                        state.value = UiState.Error(message)
                    }

                    is Either.Right -> it.data?.let { data ->
                        state.value = UiState.Success(data)
                    }
                }
            }
        }
    }
}