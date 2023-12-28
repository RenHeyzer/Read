package com.example.read.common.presentation.base

import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding
import by.kirich1409.viewbindingdelegate.viewBinding

abstract class BaseFragment<VB : ViewBinding, VM : ViewModel>(
    @LayoutRes private val contentLayoutId: Int,
) : Fragment(contentLayoutId) {

    protected abstract val binding: VB
    protected open val viewModel: VM? = null
}