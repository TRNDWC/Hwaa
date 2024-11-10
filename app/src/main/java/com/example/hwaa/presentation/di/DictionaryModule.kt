package com.example.hwaa.presentation.di

import com.example.hwaa.data.api.WordApi
import com.example.hwaa.data.repository.dictionary.DictionaryRepository
import com.example.hwaa.data.repository.dictionary.DictionaryRepositoryImpl
import com.example.hwaa.data.repository.dictionary.WordRepository
import com.example.hwaa.data.repository.dictionary.WordRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module(includes = [NetworkModule::class])
@InstallIn(SingletonComponent::class)
class DictionaryModule {
    @Singleton
    @Provides
    fun provideProductApi(retrofit: Retrofit): WordApi {
        return retrofit.create(WordApi::class.java)
    }

    @Singleton
    @Provides
    fun provideWordRepository(wordApi: WordApi): WordRepository {
        return WordRepositoryImpl(wordApi)
    }

    @Singleton
    @Provides
    fun provideDictionaryRepository(wordRepository: WordRepository): DictionaryRepository {
        return DictionaryRepositoryImpl(wordRepository)
    }
}