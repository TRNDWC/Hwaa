package com.example.hwaa.presentation.navigation.forum

import android.os.Bundle
import com.example.hwaa.presentation.core.navigatorComponent.BaseNavigator

interface ForumNavigation : BaseNavigator {
    fun fromPostToPostDetail(bundle: Bundle? = null)
    fun fromPostToCreatePost(bundle: Bundle? = null)
}