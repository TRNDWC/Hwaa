package com.example.hwaa.presentation.fragment.start

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.hwaa.R
import com.example.hwaa.presentation.activity.start.StartActivity
import com.example.hwaa.presentation.core.base.BaseFragment
import com.example.hwaa.databinding.FragmentInformationBinding
import com.example.hwaa.domain.Response
import com.example.hwaa.domain.entity.UserEntity
import com.example.hwaa.presentation.extension.setOnRightDrawableClickListener
import com.example.hwaa.presentation.extension.validate
import com.example.hwaa.presentation.navigation.start.StartNavigation
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class InformationFragment :
    BaseFragment<FragmentInformationBinding, StartViewModel>(R.layout.fragment_information) {

    @Inject
    lateinit var startNavigation: StartNavigation
    private val viewModel: StartViewModel by viewModels()
    override fun getVM() = viewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            ivBack.setOnClickListener {
                startNavigation.navigateUp()
            }

            etPassword.validate { etPassword.error = null }
            etConfirmPassword.validate { etConfirmPassword.error = null }
            etEmail.validate { etEmail.error = null }

            etPassword.setOnRightDrawableClickListener {
                viewModel.isPasswordVisible = !viewModel.isPasswordVisible
                etPassword.transformationMethod =
                    if (viewModel.isPasswordVisible) null else android.text.method.PasswordTransformationMethod.getInstance()
            }

            etConfirmPassword.setOnRightDrawableClickListener {
                viewModel.isConfirmPasswordVisible = !viewModel.isConfirmPasswordVisible
                etConfirmPassword.transformationMethod =
                    if (viewModel.isConfirmPasswordVisible) null else android.text.method.PasswordTransformationMethod.getInstance()
            }


            btnSignUp.setOnClickListener {
                val email = etEmail.text.toString()
                val password = etPassword.text.toString()
                val confirmPassword = etConfirmPassword.text.toString()
                val displayName = etFullName.text.toString()
                if (email.isNotEmpty() && password.isNotEmpty() && confirmPassword.isNotEmpty()) {
                    if (password == confirmPassword) {
                        val user = UserEntity(
                            email = email,
                            password = password,
                            displayName = displayName
                        )
                        viewModel.register(user)
                    } else {
                        etConfirmPassword.error = getString(R.string.password_not_match)
                    }
                } else {
                    let {
                        if (email.isEmpty()) {
                            etEmail.error = getString(R.string.email_empty)
                        }
                        if (password.isEmpty()) {
                            etPassword.error = getString(R.string.password_empty)
                        }
                        if (confirmPassword.isEmpty()) {
                            etConfirmPassword.error = getString(R.string.confirm_password_empty)
                        }
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.registerFlow.collect { response ->
                when (response) {
                    is Response.Success -> {
                        (activity as StartActivity).moveToMainActivity()
                    }
                    is Response.Error -> {
                        response.exception.let {
                            AlertDialog.Builder(requireContext())
                                .setTitle("Error")
                                .setMessage(it)
                                .setPositiveButton("OK") { dialog, _ ->
                                    dialog.dismiss()
                                }
                                .show()
                        }
                    }
                }
            }
        }
    }
}