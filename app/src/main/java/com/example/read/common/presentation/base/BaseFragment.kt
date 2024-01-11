package com.example.read.common.presentation.base

import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.viewbinding.ViewBinding
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.read.common.presentation.utils.UiState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

abstract class BaseFragment<VB : ViewBinding, VM : ViewModel>(
    @LayoutRes private val contentLayoutId: Int,
) : Fragment(contentLayoutId) {

    protected abstract val binding: VB
    protected open val viewModel: VM? = null

    protected open fun Fragment.launchWithViewLifecycle(
        lifecycleState: Lifecycle.State = Lifecycle.State.STARTED,
        block: suspend CoroutineScope.() -> Unit
    ) {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(lifecycleState) {
                block()
            }
        }
    }

    protected open fun <T> StateFlow<UiState<T>>.collectUiStateFlow(
        latest: Boolean = false,
        lifecycleState: Lifecycle.State = Lifecycle.State.STARTED,
        onLoading: (UiState.Loading<T>) -> Unit,
        onError: (UiState.Error<T>) -> Unit,
        onSuccess: (UiState.Success<T>) -> Unit,
    ) {
        launchWithViewLifecycle(lifecycleState = lifecycleState) {
            if (!latest) {
                this@collectUiStateFlow.collect {
                    when (it) {
                        is UiState.Loading -> onLoading(it)
                        is UiState.Error -> onError(it)
                        is UiState.Success -> onSuccess(it)
                    }
                }
            } else {
                this@collectUiStateFlow.collectLatest {
                    when (it) {
                        is UiState.Loading -> onLoading(it)
                        is UiState.Error -> onError(it)
                        is UiState.Success -> onSuccess(it)
                    }
                }
            }
        }
    }
}