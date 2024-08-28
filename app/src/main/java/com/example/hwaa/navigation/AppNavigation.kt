package com.example.hwaa.navigation

import android.os.Bundle
import com.example.hwaa.core.navigatorComponent.BaseNavigator

interface AppNavigation : BaseNavigator {
    fun navigateToBook(bundle: Bundle? = null)
    fun navigateToVocabulary(bundle: Bundle? = null)
    fun navigateToProfile(bundle: Bundle? = null)
    fun navigateToForum(bundle: Bundle? = null)
}