package com.example.hwaa.presentation.navigation.book

import android.os.Bundle
import com.example.hwaa.R
import com.example.hwaa.presentation.core.navigatorComponent.BaseNavigatorImpl
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@ActivityScoped
class BookNavigationImpl @Inject constructor() : BaseNavigatorImpl(), BookNavigation {
    override fun fromBookToLesson(bundle: Bundle?) {
        openScreen(R.id.action_lessonListFragment_to_lessonFragment, bundle)
    }
}