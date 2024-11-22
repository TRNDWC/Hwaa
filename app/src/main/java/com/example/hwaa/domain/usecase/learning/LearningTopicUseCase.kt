package com.example.hwaa.domain.usecase.learning

import com.example.hwaa.data.repository.learning.LearningRepository
import com.example.hwaa.domain.Response
import com.example.hwaa.domain.entity.TopicStatEntity
import kotlinx.coroutines.flow.Flow
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