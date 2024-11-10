package com.example.hwaa.data.repository.dictionary

import com.example.hwaa.domain.Response
import com.example.hwaa.domain.entity.WordEntity
import kotlinx.coroutines.flow.Flow

interface DictionaryRepository {
    suspend fun getWord(wordEntity: WordEntity): Flow<Response<WordEntity>>
}