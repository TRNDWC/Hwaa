package com.example.hwaa.data.repository.dictionary

import com.example.hwaa.data.api.WordApi
import com.example.hwaa.domain.Response
import com.example.hwaa.domain.entity.PronunciationEntity
import com.example.hwaa.domain.entity.WordBasicEntity
import com.example.hwaa.domain.entity.WordExampleEntity
import dagger.hilt.android.scopes.ActivityScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import timber.log.Timber
import javax.inject.Inject

@ActivityScoped
class WordRepositoryImpl @Inject constructor(
    private val wordApi: WordApi
) : WordRepository {
    override suspend fun getWordBasic(word: String): Flow<Response<WordBasicEntity>> = flow {
        try {
            val response = wordApi.getWordBasic(word)
            if (response.isSuccessful) {
                emit(Response.Success(response.body()!!))
            } else {
                emit(Response.Error(response.message()))
            }
        } catch (e: Exception) {
            emit(Response.Error(e.message ?: "An error occurred"))
        }
    }

    override suspend fun getWordPronunciation(word: String): Flow<Response<PronunciationEntity>> =
        flow {
            try {
                val response = wordApi.getWordPronunciation(word)
                if (response.isSuccessful) {
                    emit(Response.Success(response.body()!!))
                } else {
                    emit(Response.Error(response.message()))
                }
            } catch (e: Exception) {
                emit(Response.Error(e.message ?: "An error occurred"))
            }
        }

    override suspend fun getWordExample(
        word: String, level: String
    ): Flow<Response<List<WordExampleEntity>>> = flow {
        try {
            val response = wordApi.getWordExample(word, level)
            if (response.isSuccessful) {
                emit(Response.Success(response.body()!!))
            } else {
                emit(Response.Error(response.message()))
            }
        } catch (e: Exception) {
            emit(Response.Error(e.message ?: "An error occurred"))
        }
    }

}