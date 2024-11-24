package com.example.hwaa.presentation.di

import com.example.hwaa.data.repository.learning.LearningRepository
import com.example.hwaa.data.repository.learning.LearningRepositoryImpl
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class LearningModule {

    @Provides
    @Singleton
    fun provideLearningRepository(
        realtimeDatabase: FirebaseDatabase,
        storage: FirebaseStorage
    ): LearningRepository {
        return LearningRepositoryImpl(realtimeDatabase, storage)
    }
}