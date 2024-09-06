package com.example.hwaa.navigation.start

import com.example.hwaa.core.navigatorComponent.BaseNavigator

interface StartNavigation : BaseNavigator {
    fun fromStartToSignUp()
    fun fromStartToLogin()
    fun fromSignUpToInformation()
}