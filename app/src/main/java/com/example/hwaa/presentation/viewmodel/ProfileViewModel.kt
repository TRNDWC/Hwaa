package com.example.hwaa.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.example.hwaa.domain.usecase.auth.LogoutUseCase
import com.example.hwaa.presentation.core.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val logoutUseCase: LogoutUseCase
) : BaseViewModel() {

    fun logout() = viewModelScope.launch {
        logoutUseCase.invoke()
    }
}