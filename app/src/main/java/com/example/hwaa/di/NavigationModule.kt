package com.example.hwaa.di

import com.example.hwaa.core.navigatorComponent.BaseNavigator
import com.example.hwaa.navigation.AppNavigation
import com.example.hwaa.navigation.AppNavigatorImpl
import com.example.hwaa.navigation.book.BookNavigation
import com.example.hwaa.navigation.book.BookNavigationImpl
import com.example.hwaa.navigation.forum.ForumNavigation
import com.example.hwaa.navigation.forum.ForumNavigationImpl
import com.example.hwaa.navigation.profile.ProfileNavigation
import com.example.hwaa.navigation.profile.ProfileNavigationImpl
import com.example.hwaa.navigation.start.StartNavigation
import com.example.hwaa.navigation.start.StartNavigationImpl
import com.example.hwaa.navigation.vocabulary.VocabularyNavigation
import com.example.hwaa.navigation.vocabulary.VocabularyNavigationImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped

@Module
@InstallIn(ActivityComponent::class)
abstract class NavigationModule {

    @Binds
    @ActivityScoped
    abstract fun provideBaseNavigationApp(navigation: AppNavigatorImpl): BaseNavigator

    @Binds
    @ActivityScoped
    abstract fun provideAppNavigation(navigation: AppNavigatorImpl): AppNavigation

    @Binds
    @ActivityScoped
    abstract fun provideBaseNavigationStart(navigation: StartNavigationImpl): BaseNavigator

    @Binds
    @ActivityScoped
    abstract fun provideStartNavigation(navigation: StartNavigationImpl): StartNavigation


    @Binds
    @ActivityScoped
    abstract fun provideBaseNavigationBook(navigation: BookNavigationImpl): BaseNavigator

    @Binds
    @ActivityScoped
    abstract fun provideBookNavigation(navigation: BookNavigationImpl): BookNavigation

    @Binds
    @ActivityScoped
    abstract fun provideBaseNavigationForum(navigation: ForumNavigationImpl): BaseNavigator

    @Binds
    @ActivityScoped
    abstract fun provideForumNavigation(navigation: ForumNavigationImpl): ForumNavigation

    @Binds
    @ActivityScoped
    abstract fun provideBaseNavigationVocabulary(navigation: VocabularyNavigationImpl): BaseNavigator

    @Binds
    @ActivityScoped
    abstract fun provideVocabularyNavigation(navigation: VocabularyNavigationImpl): VocabularyNavigation


    @Binds
    @ActivityScoped
    abstract fun provideBaseNavigationProfile(navigation: ProfileNavigationImpl): BaseNavigator

    @Binds
    @ActivityScoped
    abstract fun provideProfileNavigation(navigation: ProfileNavigationImpl): ProfileNavigation
}
