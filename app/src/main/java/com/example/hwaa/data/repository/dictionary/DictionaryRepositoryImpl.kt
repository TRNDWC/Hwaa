package com.example.hwaa.data.repository.dictionary

import com.example.hwaa.domain.Response
import com.example.hwaa.domain.entity.WordEntity
import com.example.hwaa.presentation.util.UserProvider
import dagger.hilt.android.scopes.ActivityScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@ActivityScoped
class DictionaryRepositoryImpl @Inject constructor(
    private val wordRepository: WordRepository
) : DictionaryRepository {
    override suspend fun getWord(wordEntity: WordEntity): Flow<Response<WordEntity>> {
        return flow {
            val word = wordEntity.wordBasic.simplified
            val user = UserProvider.getUser()

            combine(
                wordRepository.getWordBasic(word),
                wordRepository.getWordPronunciation(word),
                wordRepository.getWordExample(word, "Elementary")
            ) { basic, pronunciation, example ->
                Triple(basic, pronunciation, example)
            }.map { (basic, pronunciation, example) ->
                if (basic is Response.Success &&
                    pronunciation is Response.Success &&
                    example is Response.Success) {
                    Response.Success(WordEntity(
                        basic.data,
                        pronunciation.data,
                        example.data
                    ))
                } else {
                    Response.Error("An error occurred")
                }
            }.catch { e ->
                emit(Response.Error(e.message ?: "An error occurred"))
            }.collect { response ->
                emit(response)
            }
        }
    }
}