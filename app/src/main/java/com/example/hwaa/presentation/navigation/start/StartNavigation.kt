package com.example.hwaa.presentation.navigation.start

import com.example.hwaa.presentation.core.navigatorComponent.BaseNavigator

interface StartNavigation : BaseNavigator {
    fun fromStartToSignUp()
    fun fromStartToLogin()
    fun fromSignUpToInformation()
}