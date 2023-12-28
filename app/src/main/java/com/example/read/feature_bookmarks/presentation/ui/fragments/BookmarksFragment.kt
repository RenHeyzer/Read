package com.example.read.feature_bookmarks.presentation.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.read.R
import com.example.read.common.presentation.base.BaseFragment
import com.example.read.databinding.FragmentBookmarksBinding
import com.example.read.feature_bookmarks.presentation.viewmodels.BookmarksViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BookmarksFragment :
    BaseFragment<FragmentBookmarksBinding, BookmarksViewModel>(R.layout.fragment_bookmarks) {

    override val binding by viewBinding(FragmentBookmarksBinding::bind)
    override val viewModel by viewModels<BookmarksViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

}