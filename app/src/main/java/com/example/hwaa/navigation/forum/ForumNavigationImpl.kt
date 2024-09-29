package com.example.hwaa.navigation.forum

import android.os.Bundle
import com.example.hwaa.R
import com.example.hwaa.core.navigatorComponent.BaseNavigatorImpl
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@ActivityScoped
class ForumNavigationImpl @Inject constructor() : BaseNavigatorImpl(), ForumNavigation {
    override fun fromPostToPostDetail(bundle: Bundle?) {
        openScreen(R.id.action_postFragment_to_postDetailFragment, bundle)
    }

    override fun fromPostToCreatePost(bundle: Bundle?) {
        openScreen(R.id.action_postFragment_to_createPostFragment, bundle)
    }
}