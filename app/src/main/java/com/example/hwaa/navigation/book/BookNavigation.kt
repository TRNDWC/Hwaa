package com.example.hwaa.navigation.book

import android.os.Bundle
import com.example.hwaa.core.navigatorComponent.BaseNavigator

interface BookNavigation : BaseNavigator {
    fun fromBookToLesson(bundle: Bundle?)
}