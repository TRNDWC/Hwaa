package com.example.hwaa.data.repository.learning

import com.example.hwaa.domain.Response
import com.example.hwaa.domain.entity.TopicStatEntity
import kotlinx.coroutines.flow.Flow

interface LearningRepository {
    suspend fun getTopics(): Flow<Response<List<TopicStatEntity>>>
    suspend fun updateTopic(topic: TopicStatEntity): Flow<Response<Boolean>>
}