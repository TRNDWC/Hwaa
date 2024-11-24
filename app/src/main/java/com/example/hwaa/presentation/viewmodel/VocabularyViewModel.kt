package com.example.hwaa.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.example.hwaa.data.model.TestModel
import com.example.hwaa.data.model.WordStatModel
import com.example.hwaa.domain.Response
import com.example.hwaa.domain.usecase.learning.GetTestListUseCase
import com.example.hwaa.domain.usecase.learning.GetWordStatListUseCase
import com.example.hwaa.domain.usecase.learning.UpdateWordStatUseCase
import com.example.hwaa.presentation.core.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class VocabularyViewModel @Inject constructor(
    private val getWordStatListUseCase: GetWordStatListUseCase,
    private val getTestListUseCase: GetTestListUseCase,
    private val updateWordStatUseCase: UpdateWordStatUseCase
) : BaseViewModel() {

    private var _getWordStatFlow: MutableSharedFlow<Response<List<WordStatModel>>> =
        MutableSharedFlow()
    val getWordStatFlow = _getWordStatFlow

    private var _getTestListFlow: MutableSharedFlow<Response<List<TestModel>>> =
        MutableSharedFlow()
    val getTestListFlow = _getTestListFlow

    var selectedChallenge: TestModel? = null
    var updateWordList: MutableList<WordStatModel> = mutableListOf()

    fun getWordStatList() {
        viewModelScope.launch {
            getWordStatListUseCase.invoke().collect {
                _getWordStatFlow.emit(it)
            }
        }
    }

    fun getTestList() {
        Timber.tag("trndwcs").d("getTestList")
        viewModelScope.launch {
            getTestListUseCase.invoke().collect { response ->
                _getTestListFlow.emit(response)
            }
        }
    }

    fun updateWordStatList() {
        viewModelScope.launch {
            updateWordList.forEach { wordStatModel ->
                updateWordStatUseCase.invoke(
                    wordStatModel.word.id,
                    wordStatModel.score,
                    wordStatModel.isRemembered
                ).collect {
                    Timber.tag("trndwcs").d("updateWordStatList $it")
                }
            }
        }
    }

}