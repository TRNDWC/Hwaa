package com.example.hwaa.fragment.start

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.hwaa.R
import com.example.hwaa.activity.start.StartActivity
import com.example.hwaa.core.base.BaseFragment
import com.example.hwaa.databinding.FragmentInformationBinding
import com.example.hwaa.extension.setOnRightDrawableClickListener
import com.example.hwaa.extension.validate
import com.example.hwaa.navigation.start.StartNavigation
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
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
            etEmail.validate {
                viewModel.email = it
            }
            etPassword.validate {
                viewModel.password = it
            }
            etPassword.setOnRightDrawableClickListener {
                viewModel.isPasswordVisible = !viewModel.isPasswordVisible
                etPassword.transformationMethod =
                    if (viewModel.isPasswordVisible) null else android.text.method.PasswordTransformationMethod.getInstance()
            }
            btnSignUp.setOnClickListener {
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