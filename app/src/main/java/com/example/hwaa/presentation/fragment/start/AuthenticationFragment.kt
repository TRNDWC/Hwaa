package com.example.hwaa.presentation.fragment.start

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.example.hwaa.R
import com.example.hwaa.presentation.activity.start.StartActivity
import com.example.hwaa.presentation.core.base.BaseFragment
import com.example.hwaa.databinding.FragmentAuthenticationBinding
import com.example.hwaa.presentation.extension.setOnRightDrawableClickListener
import com.example.hwaa.presentation.extension.validate
import com.example.hwaa.presentation.navigation.start.StartNavigation
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class AuthenticationFragment : BaseFragment<FragmentAuthenticationBinding, StartViewModel>(R.layout.fragment_authentication) {

    @Inject
    lateinit var startNavigation: StartNavigation
    private val viewModel: StartViewModel by viewModels()
    override fun getVM() = viewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            etEmail.validate {
                viewModel.email = it
            }
            etPassword.validate {
                viewModel.password = it
            }
            etPassword.setOnRightDrawableClickListener {
                viewModel.isPasswordVisible = !viewModel.isPasswordVisible
                etPassword.transformationMethod = if (viewModel.isPasswordVisible) null else android.text.method.PasswordTransformationMethod.getInstance()
            }
            btnLogin.setOnClickListener {
                CoroutineScope(Dispatchers.Main).launch {
                    (activity as StartActivity).moveToMainActivity()
                }
            }
            ivBack.setOnClickListener {
                startNavigation.navigateUp()
            }
        }
    }
}