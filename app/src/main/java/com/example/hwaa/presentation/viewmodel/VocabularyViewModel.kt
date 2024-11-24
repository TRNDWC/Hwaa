package com.example.hwaa.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.example.hwaa.data.model.WordStatModel
import com.example.hwaa.domain.Response
import com.example.hwaa.domain.usecase.learning.GetWordStatListUseCase
import com.example.hwaa.presentation.core.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VocabularyViewModel @Inject constructor(
    private val getWordStatListUseCase: GetWordStatListUseCase
) : BaseViewModel() {

    private var _getWordStatFlow: MutableSharedFlow<Response<List<WordStatModel>>> =
        MutableSharedFlow()
    val getWordStatFlow = _getWordStatFlow

    fun getWordStatList() {
        viewModelScope.launch {
            getWordStatListUseCase.invoke().collect {
                _getWordStatFlow.emit(it)
            }
        }
    }

}