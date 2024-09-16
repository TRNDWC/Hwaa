package com.example.hwaa.navigation

import android.os.Bundle
import com.example.hwaa.R
import com.example.hwaa.core.navigatorComponent.BaseNavigatorImpl
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@ActivityScoped
class AppNavigatorImpl @Inject constructor() : BaseNavigatorImpl(),
    AppNavigation {
    override fun fromBookToLesson(bundle: Bundle?) {
        openScreen(R.id.action_lessonListFragment_to_lessonFragment, bundle)
    }
}