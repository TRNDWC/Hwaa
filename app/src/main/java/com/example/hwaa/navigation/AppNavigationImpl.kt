package com.example.hwaa.navigation

import android.os.Bundle
import com.example.hwaa.R
import com.example.hwaa.core.navigatorComponent.BaseNavigatorImpl
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@ActivityScoped
class AppNavigatorImpl @Inject constructor() : BaseNavigatorImpl(),
    AppNavigation {
    override fun navigateToBook(bundle: Bundle?) {
//        openScreen(R.id.action_homeFragment_to_bookFragment, bundle)
    }

    override fun navigateToVocabulary(bundle: Bundle?) {
//        openScreen(R.id.action_homeFragment_to_vocabularyFragment, bundle)
    }

    override fun navigateToProfile(bundle: Bundle?) {
//        openScreen(R.id.action_homeFragment_to_profileFragment, bundle)
    }

    override fun navigateToForum(bundle: Bundle?) {
//        openScreen(R.id.action_homeFragment_to_forumFragment, bundle)
    }
}