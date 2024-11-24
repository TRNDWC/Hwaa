package com.example.hwaa.presentation.di

import com.example.hwaa.data.repository.blog.ForumRepository
import com.example.hwaa.data.repository.blog.ForumRepositoryImpl
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ForumModule {

    @Provides
    @Singleton
    fun provideForumRepository(
        realtimeDatabase: FirebaseDatabase,
        storage: FirebaseStorage
    ): ForumRepository {
        return ForumRepositoryImpl(realtimeDatabase, storage)
    }
}