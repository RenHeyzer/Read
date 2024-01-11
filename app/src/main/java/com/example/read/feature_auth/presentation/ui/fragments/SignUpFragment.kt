package com.example.read.feature_auth.presentation.ui.fragments

import android.content.res.ColorStateList
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.read.R
import com.example.read.common.presentation.base.BaseFragment
import com.example.read.common.presentation.utils.getAttributeColor
import com.example.read.common.presentation.utils.notNullContext
import com.example.read.databinding.FragmentSignUpBinding
import com.example.read.feature_auth.presentation.models.LoginType
import com.example.read.feature_auth.presentation.utils.ExceptionMessages
import com.example.read.feature_auth.presentation.utils.ValidatePropertiesState
import com.example.read.feature_auth.presentation.utils.countDownTimer
import com.example.read.feature_auth.presentation.utils.enableTextInputLayoutsError
import com.example.read.feature_auth.presentation.utils.validateProperties
import com.example.read.feature_auth.presentation.viewmodels.AuthViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.TimeUnit
import javax.inject.Inject

private const val MILLS_IN_FUTURE_KEY = "millsInFuture"

@AndroidEntryPoint
class SignUpFragment :
    BaseFragment<FragmentSignUpBinding, AuthViewModel>(R.layout.fragment_sign_up) {

    override val binding by viewBinding(FragmentSignUpBinding::bind)
    override val viewModel by viewModels<AuthViewModel>()
    private var millsInFuture: Long = 60000

    @Inject
    lateinit var exceptionMessages: ExceptionMessages

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        savedInstanceState?.let {
            millsInFuture = it.getLong(MILLS_IN_FUTURE_KEY)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        onSignUpButtonClick()
        subscribeToSignUpState(view)
        subscribeToSignInState(view)
        onCheckIsEmailVerifiedClick(view)
        onAlreadyHaveAnAccount()
    }

    private fun setupViews() {
        val colorStateList = ColorStateList(
            arrayOf(
                intArrayOf(android.R.attr.state_pressed),
                intArrayOf(android.R.attr.state_enabled),
            ),
            intArrayOf(
                notNullContext.getAttributeColor(com.google.android.material.R.attr.colorTertiary),
                notNullContext.getAttributeColor(android.R.attr.textColorSecondary),
            )
        )
        binding.tvAlreadyHaveAnAccount.setTextColor(colorStateList)
    }

    private fun onSignUpButtonClick() = with(binding) {
        btnSignUp.setOnClickListener {
            when (val result = validate()) {
                is ValidatePropertiesState.Error -> {
                    enableTextInputLayoutsError(
                        tilUsername = tilUsername,
                        tilEmail = tilEmail,
                        tilPassword = tilPassword,
                        tilConfirmPassword = tilConfirmPassword,
                        empty = result.empty
                    )
                }

                is ValidatePropertiesState.Success -> {
                    viewModel.login(type = result.type, exceptionMessages = exceptionMessages)
                }
            }
        }
    }

    private fun validate(): ValidatePropertiesState = with(binding) {
        val username = etUsername.text.toString().trim()
        val email = etEmail.text.toString().trim()
        val password = etPassword.text.toString().trim()
        val confirmPassword = etConfirmPassword.text.toString().trim()

        return validateProperties(
            LoginType.SignUp(
                username = username,
                email = email,
                password = password,
                confirmPassword = confirmPassword
            )
        )
    }

    private val confirmationTimer: CountDownTimer by lazy {
        with(binding.confirmationEmailContent) {
            countDownTimer(
                millsInFuture = millsInFuture,
                countDownInterval = 1000,
                onFinish = {
                    tvUntilResending.isVisible = false
                    tvResend.isVisible = true
                },
                onTick = { millisUntilFinished ->
                    millsInFuture = millisUntilFinished
                    val secondsUntilFinished =
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished)
                    tvUntilResending.text =
                        getString(
                            R.string.until_resending_confirmation_email,
                            secondsUntilFinished.toString()
                        )
                }
            )
        }
    }

    private fun subscribeToSignUpState(view: View) {
        viewModel.signUpState.collectUiStateFlow(
            latest = true,
            lifecycleState = Lifecycle.State.CREATED,
            onLoading = {
                Log.i("signUp", "loading")
            },
            onError = {
                val message = it.message ?: getString(R.string.error_message)
                Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show()
            },
            onSuccess = {
                if (it.data != null && it.data) {
                    val confirmationEmailSentMessage =
                        getString(R.string.confirmation_email_sent)
                    Snackbar.make(view, confirmationEmailSentMessage, Snackbar.LENGTH_SHORT)
                        .show()

                    binding.btnSignUp.isVisible = false
                    binding.confirmationEmailContent.root.isVisible = true
                    confirmationTimer.start()
                }
            }
        )
    }

    private fun subscribeToSignInState(view: View) {
        viewModel.signUpState.collectUiStateFlow(
            latest = true,
            lifecycleState = Lifecycle.State.CREATED,
            onLoading = {
                Log.i("signUp", "loading")
            },
            onError = {
                val message = it.message ?: getString(R.string.error_message)
                Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show()
            },
            onSuccess = {
                if (it.data != null && it.data) {
                    val confirmationEmailSentMessage =
                        getString(R.string.confirmation_email_sent)
                    Snackbar.make(view, confirmationEmailSentMessage, Snackbar.LENGTH_SHORT)
                        .show()

                    binding.btnSignUp.isVisible = false
                    binding.confirmationEmailContent.root.isVisible = true
                    confirmationTimer.start()
                }
            }
        )
    }

    private fun onCheckIsEmailVerifiedClick(view: View) {
        binding.confirmationEmailContent.btnCheck.setOnClickListener {
            if (viewModel.isEmailVerified) {
                findNavController().navigate(R.id.signUpSuccessDialogFragment)
            } else {
                val message = getString(R.string.email_not_confirmed)
                Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    private fun onAlreadyHaveAnAccount() {
        binding.tvAlreadyHaveAnAccount.setOnClickListener {
            findNavController().navigate(R.id.action_signUpFragment_to_singInFragment)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putLong(MILLS_IN_FUTURE_KEY, millsInFuture)
    }
}