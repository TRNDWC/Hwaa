package com.example.hwaa.data.repository.dictionary

import com.example.hwaa.domain.Response
import com.example.hwaa.domain.entity.PronunciationEntity
import com.example.hwaa.domain.entity.WordBasicEntity
import com.example.hwaa.domain.entity.WordExampleEntity
import kotlinx.coroutines.flow.Flow

interface WordRepository {
    suspend fun getWordBasic(word: String): Flow<Response<WordBasicEntity>>
    suspend fun getWordPronunciation(word: String): Flow<Response<PronunciationEntity>>
    suspend fun getWordExample(word: String, level: String): Flow<Response<List<WordExampleEntity>>>
}