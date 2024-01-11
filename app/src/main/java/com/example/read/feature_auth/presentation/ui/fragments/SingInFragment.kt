package com.example.read.feature_auth.presentation.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.read.R
import com.example.read.common.presentation.base.BaseFragment
import com.example.read.databinding.FragmentSignInBinding
import com.example.read.feature_auth.presentation.viewmodels.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SingInFragment : BaseFragment<FragmentSignInBinding, AuthViewModel>(R.layout.fragment_sign_in) {

    override val binding by viewBinding(FragmentSignInBinding::bind)
    override val viewModel by viewModels<AuthViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onSingUpTextClick()
    }

    private fun onSingUpTextClick() {
        binding.tvSignUp.setOnClickListener {
            findNavController().navigate(R.id.action_singInFragment_to_signUpFragment)
        }
    }
}