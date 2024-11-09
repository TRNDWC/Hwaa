package com.example.hwaa.presentation.fragment.start

import androidx.lifecycle.viewModelScope
import com.example.hwaa.BuildConfig
import com.example.hwaa.domain.Response
import com.example.hwaa.domain.entity.UserEntity
import com.example.hwaa.domain.usecase.auth.IsLoggedInUseCase
import com.example.hwaa.domain.usecase.auth.LoginUseCase
import com.example.hwaa.domain.usecase.auth.RegisterUseCase
import com.example.hwaa.domain.usecase.auth.ResetPasswordUseCase
import com.example.hwaa.presentation.core.base.BaseViewModel
import com.google.firebase.auth.AuthResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject


@HiltViewModel
class StartViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val resetPasswordUseCase: ResetPasswordUseCase,
    private val registerUseCase: RegisterUseCase,
    private val isLoggedInUseCase: IsLoggedInUseCase
) : BaseViewModel() {

    var isConfirmPasswordVisible: Boolean = false
    var isPasswordVisible: Boolean = false
    private var _registerFlow = MutableSharedFlow<Response<AuthResult>>()
    val registerFlow = _registerFlow

    private val _loginFlow = MutableSharedFlow<Response<AuthResult>>()
    val loginFlow = _loginFlow

    private val _resetPasswordFlow = MutableSharedFlow<Response<Void?>>()
    val resetPasswordFlow = _resetPasswordFlow

    private var _isLoggedIn : Boolean = false
    val isLoggedIn : Boolean
        get() = _isLoggedIn

    fun login(user: UserEntity) = viewModelScope.launch {
        loginUseCase.invoke(user).collect { response ->
            _loginFlow.emit(response)
        }
    }

    fun resetPassword(email: String) = viewModelScope.launch {
        resetPasswordUseCase.invoke(email).collect { response ->
            _resetPasswordFlow.emit(response)
        }
    }

    fun register(user: UserEntity) = viewModelScope.launch {
        registerUseCase.invoke(user).collect { response ->
            _registerFlow.emit(response)
        }
    }

    fun checkLogin() = viewModelScope.launch {
        isLoggedInUseCase.invoke().collect { response ->
            _isLoggedIn = response
        }
    }

}