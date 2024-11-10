package com.example.hwaa.domain.usecase.dictionary

import com.example.hwaa.data.repository.dictionary.DictionaryRepository
import com.example.hwaa.domain.entity.WordEntity
import javax.inject.Inject

class DictionaryUseCase @Inject constructor(
    private val dictionaryRepository: DictionaryRepository
) {
    suspend operator fun invoke(word: WordEntity) = dictionaryRepository.getWord(word)
}