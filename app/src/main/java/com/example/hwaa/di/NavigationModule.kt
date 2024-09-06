package com.example.hwaa.di

import com.example.hwaa.core.navigatorComponent.BaseNavigator
import com.example.hwaa.navigation.AppNavigation
import com.example.hwaa.navigation.AppNavigatorImpl
import com.example.hwaa.navigation.start.StartNavigation
import com.example.hwaa.navigation.start.StartNavigationImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped

@Module
@InstallIn(ActivityComponent::class)
abstract class NavigationModule {

//    @Binds
//    @ActivityScoped
//    abstract fun provideBaseNavigation(navigation: AppNavigatorImpl): BaseNavigator

    @Binds
    @ActivityScoped
    abstract fun provideAppNavigation(navigation: AppNavigatorImpl): AppNavigation


//    @Binds
//    @ActivityScoped
//    abstract fun provideBaseStartNavigation(navigation: StartNavigationImpl): BaseNavigator

    @Binds
    @ActivityScoped
    abstract fun provideStartNavigation(navigation: StartNavigationImpl): StartNavigation
}
