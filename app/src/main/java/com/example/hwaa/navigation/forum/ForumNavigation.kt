package com.example.hwaa.navigation.forum

import android.os.Bundle
import com.example.hwaa.core.navigatorComponent.BaseNavigator

interface ForumNavigation : BaseNavigator {
    fun fromPostToPostDetail(bundle: Bundle? = null)
    fun fromPostToCreatePost(bundle: Bundle? = null)
}