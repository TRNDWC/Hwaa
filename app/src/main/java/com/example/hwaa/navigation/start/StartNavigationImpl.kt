package com.example.hwaa.navigation.start

import com.example.hwaa.R
import com.example.hwaa.core.navigatorComponent.BaseNavigatorImpl
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@ActivityScoped
class StartNavigationImpl @Inject constructor() : BaseNavigatorImpl(),
    StartNavigation {
    override fun fromStartToSignUp() {
        openScreen(R.id.action_startFragment_to_authenticationFragment)
    }

    override fun fromStartToLogin() {
        openScreen(R.id.action_startFragment_to_authenticationFragment)
    }

    override fun fromSignUpToInformation() {
        openScreen(R.id.action_authenticationFragment_to_informationFragment)
    }
}