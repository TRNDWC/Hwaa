package com.example.hwaa.data.repository.learning

import com.example.hwaa.domain.Response
import com.example.hwaa.domain.entity.TestEntity
import com.example.hwaa.domain.entity.TopicStatEntity
import com.example.hwaa.domain.entity.WordStatEntity
import kotlinx.coroutines.flow.Flow

interface LearningRepository {
    suspend fun getTopics(): Flow<Response<List<TopicStatEntity>>>
    suspend fun updateTopic(topic: TopicStatEntity): Flow<Response<Boolean>>
    suspend fun getWordStatList(): Flow<Response<List<WordStatEntity>>>
    suspend fun getPushWordStat() : Flow<Response<WordStatEntity>>
    suspend fun updateWordStat(wordId: String, score:Int, isRemembered: Boolean): Flow<Response<Boolean>>
    suspend fun getTestList(): Flow<Response<List<TestEntity>>>
}