package com.example.hwaa.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.example.hwaa.domain.Response
import com.example.hwaa.domain.entity.WordEntity
import com.example.hwaa.domain.usecase.dictionary.DictionaryUseCase
import com.example.hwaa.presentation.core.base.BaseViewModel
import com.google.firebase.auth.AuthResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class DictionaryViewModel @Inject constructor(
    private val dictionaryUseCase: DictionaryUseCase
) : BaseViewModel() {

    private val _searchFlow = MutableSharedFlow<Response<WordEntity>>()
    val searchFlow = _searchFlow

    fun searchWord(word: WordEntity) = viewModelScope.launch {
        dictionaryUseCase.invoke(word).collect { response ->
            _searchFlow.emit(response)
        }
    }
}