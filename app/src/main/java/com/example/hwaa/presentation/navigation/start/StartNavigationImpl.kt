package com.example.hwaa.presentation.navigation.start

import com.example.hwaa.R
import com.example.hwaa.presentation.core.navigatorComponent.BaseNavigatorImpl
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@ActivityScoped
class StartNavigationImpl @Inject constructor() : BaseNavigatorImpl(),
    StartNavigation {
    override fun fromStartToSignUp() {
        openScreen(R.id.action_startFragment_to_informationFragment)
    }

    override fun fromStartToLogin() {
        openScreen(R.id.action_startFragment_to_authenticationFragment)
    }

    override fun fromSignUpToInformation() {
        openScreen(R.id.action_informationFragment_to_authenticationFragment)
    }
}