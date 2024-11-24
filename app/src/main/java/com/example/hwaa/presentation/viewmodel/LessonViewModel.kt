package com.example.hwaa.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.example.hwaa.data.model.LessonStatModel
import com.example.hwaa.data.model.TopicStatModel
import com.example.hwaa.data.model.WordStatModel
import com.example.hwaa.domain.Response
import com.example.hwaa.domain.entity.TopicStatEntity
import com.example.hwaa.domain.usecase.learning.GetTopicUseCase
import com.example.hwaa.domain.usecase.learning.UpdateTopicUseCase
import com.example.hwaa.presentation.core.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class LessonViewModel @Inject constructor(
    private val getTopicUseCase: GetTopicUseCase,
    private val updateTopicUseCase: UpdateTopicUseCase
) : BaseViewModel() {

    var selectedLesson: LessonStatModel? = null
    var selectedTopic: TopicStatModel? = null
    var updateWordList: MutableList<WordStatModel> = mutableListOf()


    private var _flowGetTopic = MutableSharedFlow<Response<List<TopicStatEntity>>>()
    val flowGetTopic = _flowGetTopic

    private var _flowRewind = MutableSharedFlow<Any>()
    val flowRewind = _flowRewind

    private var _flowUpdateWord = MutableSharedFlow<Response<Boolean>>()
    val flowUpdateWord = _flowUpdateWord

    private var _flowUpdateLesson = MutableSharedFlow<LessonStatModel>()
    val flowUpdateLesson = _flowUpdateLesson

    fun getTopics() {
        viewModelScope.launch {
            getTopicUseCase.invoke().collect { response ->
                _flowGetTopic.emit(response)
            }
        }
    }

    fun updateLesson(score: Int) {
        val evaluateStars = when (score * 1.0 / selectedLesson?.lessonModel?.words?.size!! * 100) {
            in 25.0..50.0 -> 1
            in 50.1..75.0 -> 2
            else -> 3
        }

        selectedLesson?.star = evaluateStars

//        update word list
        selectedLesson?.lessonModel?.words = updateWordList

//        update lesson
        selectedTopic?.topicModel?.lessons?.forEach { lesson ->
            if (lesson.lessonModel.id == selectedLesson?.lessonModel?.id) {
                lesson.star = evaluateStars
                lesson.lessonModel = selectedLesson?.lessonModel!!
            }
        }

        var learnedLesson = 0
        selectedTopic?.topicModel?.lessons?.forEach {
            if (it.star != 0) {
                learnedLesson++
            }
        }
        selectedTopic?.totalFinished = learnedLesson


        viewModelScope.launch {
            updateTopicUseCase.invoke(TopicStatEntity.translateToEntity(selectedTopic!!))
                .collect {
                    when (it) {
                        is Response.Success -> {
                            _flowUpdateLesson.emit(selectedLesson!!)
                        }

                        is Response.Error -> {
                            Timber.e(it.exception)
                        }
                    }
                }
        }
    }

    fun updateWord() {
        Timber.tag("trndwcs").d("updateWordList: ${updateWordList.size}")
        selectedLesson?.lessonModel?.words = updateWordList
        selectedTopic?.topicModel?.lessons?.forEach {
            if (it.lessonModel.id == selectedLesson?.lessonModel?.id) {
                it.lessonModel = selectedLesson?.lessonModel!!
            }
            it.isLearned = true
        }
        viewModelScope.launch {
            updateTopicUseCase.invoke(TopicStatEntity.translateToEntity(selectedTopic!!))
                .collect { response ->
                    _flowUpdateWord.emit(response)
                }
        }
    }
}