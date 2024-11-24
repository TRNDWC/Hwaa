package com.example.hwaa.domain.usecase.learning

import com.example.hwaa.data.model.TestModel
import com.example.hwaa.data.model.WordStatModel
import com.example.hwaa.data.repository.learning.LearningRepository
import com.example.hwaa.domain.Response
import com.example.hwaa.domain.entity.TestEntity
import com.example.hwaa.domain.entity.TopicStatEntity
import com.example.hwaa.domain.entity.WordStatEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import timber.log.Timber
import javax.inject.Inject

class GetTopicUseCase @Inject constructor(
    private val learningRepository: LearningRepository
) {
    suspend operator fun invoke(): Flow<Response<List<TopicStatEntity>>> {
        return learningRepository.getTopics()
    }
}

class UpdateTopicUseCase @Inject constructor(
    private val learningRepository: LearningRepository
) {
    suspend operator fun invoke(topicStatEntity: TopicStatEntity): Flow<Response<Boolean>> {
        return learningRepository.updateTopic(topicStatEntity)
    }
}

class GetWordStatListUseCase @Inject constructor(
    private val learningRepository: LearningRepository
) {
    suspend operator fun invoke(): Flow<Response<List<WordStatModel>>> {
        return learningRepository.getWordStatList().map { response ->
            when (response) {
                is Response.Success -> {
                    val wordStatList = response.data.map { wordStatEntity ->
                        WordStatEntity.translateToModel(wordStatEntity)
                    }
                    Response.Success(wordStatList)
                }

                is Response.Error -> {
                    Response.Error(response.exception)
                }
            }
        }
    }
}

class GetPushWordStatUseCase @Inject constructor(
    private val learningRepository: LearningRepository
) {
    suspend operator fun invoke(): Flow<Response<WordStatModel>> {
        return learningRepository.getPushWordStat().map { response ->
            when (response) {
                is Response.Success -> {
                    val wordStatModel = WordStatEntity.translateToModel(response.data)
                    Response.Success(wordStatModel)
                }

                is Response.Error -> {
                    Response.Error(response.exception)
                }
            }
        }
    }
}

class UpdateWordStatUseCase @Inject constructor(
    private val learningRepository: LearningRepository
) {
    suspend operator fun invoke(
        wordId: String,
        score: Int,
        isRemember: Boolean
    ): Flow<Response<Boolean>> {
        Timber.tag("trndwcs")
            .e("UpdateWordStatUseCase invoke wordId: $wordId, score: $score, isRemember: $isRemember")
        return learningRepository.updateWordStat(wordId, score, isRemember)
    }
}


class GetTestListUseCase @Inject constructor(
    private val learningRepository: LearningRepository
) {
    suspend operator fun invoke(): Flow<Response<List<TestModel>>> {
        Timber.tag("trndwcs").e("GetTestListUseCase invoke")
        return learningRepository.getTestList().map { response ->
            Timber.tag("trndwcs").e("GetTestListUseCase invoke response: $response")
            when (response) {
                is Response.Success -> {
                    val testList = response.data.map { testEntity ->
                        TestEntity.translateToModel(testEntity)
                    }
                    Response.Success(testList)
                }

                is Response.Error -> {
                    Response.Error(response.exception)
                }
            }
        }
    }
}