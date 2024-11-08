package com.example.hwaa.presentation.navigation.profile

import com.example.hwaa.R
import com.example.hwaa.presentation.core.navigatorComponent.BaseNavigatorImpl
import javax.inject.Inject


class ProfileNavigationImpl @Inject constructor() : ProfileNavigation, BaseNavigatorImpl() {
    override fun navigateToSetting() {
        openScreen(R.id.action_profileDetailedFragment_to_settingFragment)
    }
}