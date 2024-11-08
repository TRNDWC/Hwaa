package com.example.hwaa.presentation.di

import android.app.Application
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped

@Module
@InstallIn(ActivityComponent::class)
abstract class ApplicationModule {
    @Binds
    @ActivityScoped
    abstract fun provideApplication(application: Application): Application
}