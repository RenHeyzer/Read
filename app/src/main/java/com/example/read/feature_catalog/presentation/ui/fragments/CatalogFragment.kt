package com.example.read.feature_catalog.presentation.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.read.R
import com.example.read.common.presentation.base.BaseFragment
import com.example.read.databinding.FragmentCatalogBinding
import com.example.read.feature_catalog.presentation.viewmodels.CatalogViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CatalogFragment : BaseFragment<FragmentCatalogBinding, CatalogViewModel>(R.layout.fragment_catalog) {

    override val binding by viewBinding(FragmentCatalogBinding::bind)
    override val viewModel by viewModels<CatalogViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

}