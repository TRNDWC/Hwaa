package com.example.hwaa.navigation

import android.os.Bundle
import com.example.hwaa.core.navigatorComponent.BaseNavigator

interface AppNavigation : BaseNavigator {
    fun fromBookToLesson(bundle: Bundle?)
}