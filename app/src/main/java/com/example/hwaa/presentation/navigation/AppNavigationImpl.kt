package com.example.hwaa.presentation.navigation

import android.os.Bundle
import androidx.fragment.app.FragmentManager
import com.example.hwaa.R
import com.example.hwaa.presentation.core.navigatorComponent.BaseNavigatorImpl
import com.example.hwaa.presentation.fragment.main.FragmentDictionary
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@ActivityScoped
class AppNavigatorImpl @Inject constructor() : BaseNavigatorImpl(),
    AppNavigation {
    override fun fromBookToLesson(bundle: Bundle?) {
        openScreen(R.id.action_lessonListFragment_to_lessonFragment, bundle)
    }

    override fun showDictionary(childFragmentManager: FragmentManager) {
        val dictionary = FragmentDictionary()
        dictionary.show(childFragmentManager, dictionary.tag)
    }
}