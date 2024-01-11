package com.example.read.feature_auth.presentation.ui.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import com.example.read.R
import com.example.read.databinding.FragmentSignUpSuccessDialogBinding
import android.graphics.Color

class SignUpSuccessDialogFragment : DialogFragment(R.layout.fragment_sign_up_success_dialog) {

    private var _binding: FragmentSignUpSuccessDialogBinding? = null
    private val binding get() = _binding!!

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = activity?.let {
            val builder = AlertDialog.Builder(it)
            val inflater = requireActivity().layoutInflater
            _binding = FragmentSignUpSuccessDialogBinding.inflate(inflater)
            builder.setView(binding.root)
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return dialog
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onPositiveButtonClick()
    }

    private fun onPositiveButtonClick() {
        binding.btnPositive.setOnClickListener {
            findNavController().popBackStack(R.id.signUpFragment, true)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}