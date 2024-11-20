package com.example.hwaa.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.example.hwaa.data.model.UserModel
import com.example.hwaa.domain.Response
import com.example.hwaa.domain.entity.UserEntity
import com.example.hwaa.domain.usecase.auth.LogoutUseCase
import com.example.hwaa.domain.usecase.auth.UpdateProfileUseCase
import com.example.hwaa.presentation.core.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val logoutUseCase: LogoutUseCase,
    private val updateProfileUseCase: UpdateProfileUseCase
) : BaseViewModel() {

    private val _updateFlow = MutableSharedFlow<Response<Void?>>()
    val updateFlow = _updateFlow

    fun updateProfile(userModel: UserModel) = viewModelScope.launch {
        updateProfileUseCase.invoke(
            UserEntity.translateFromUserModel(userModel)
        ).collect {
            _updateFlow.emit(it)
        }
    }

    fun logout() = viewModelScope.launch {
        logoutUseCase.invoke()
    }
}