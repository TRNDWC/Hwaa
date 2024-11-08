package com.example.hwaa.presentation.navigation.book

import android.os.Bundle
import com.example.hwaa.presentation.core.navigatorComponent.BaseNavigator

interface BookNavigation : BaseNavigator {
    fun fromBookToLesson(bundle: Bundle?)
}