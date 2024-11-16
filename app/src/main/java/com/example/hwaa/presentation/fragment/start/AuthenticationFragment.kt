package com.example.hwaa.presentation.fragment.start

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.hwaa.R
import com.example.hwaa.presentation.activity.start.StartActivity
import com.example.hwaa.presentation.core.base.BaseFragment
import com.example.hwaa.databinding.FragmentAuthenticationBinding
import com.example.hwaa.domain.Response
import com.example.hwaa.domain.entity.UserEntity
import com.example.hwaa.presentation.extension.setOnRightDrawableClickListener
import com.example.hwaa.presentation.extension.validate
import com.example.hwaa.presentation.navigation.start.StartNavigation
import com.example.hwaa.presentation.util.UserProvider
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class AuthenticationFragment :
    BaseFragment<FragmentAuthenticationBinding, StartViewModel>(R.layout.fragment_authentication) {

    @Inject
    lateinit var startNavigation: StartNavigation
    private val viewModel: StartViewModel by viewModels()
    override fun getVM() = viewModel
    private var userEntity: UserEntity? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            btnLogin.setOnClickListener {
                val email = etEmail.text.toString()
                val password = etPassword.text.toString()
                if (email.isNotEmpty() && password.isNotEmpty()) {
                    userEntity = UserEntity(email, password)
                    userEntity?.let {
                        viewModel.login(it)
                        (activity as StartActivity).showLoading()
                    }
                } else {
                    let {
                        if (email.isEmpty()) {
                            etEmail.error = getString(R.string.email_empty)
                        }
                        if (password.isEmpty()) {
                            etPassword.error = getString(R.string.password_empty)
                        }
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.loginFlow.collect { response ->
                (activity as StartActivity).hiddenLoading()
                when (response) {
                    is Response.Success -> {
                        (activity as StartActivity).moveToMainActivity()
                        userEntity?.let { UserProvider.saveUser(it.translateToUserModel()) }
                    }

                    is Response.Error -> {
                        response.exception.let {
                            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }
}