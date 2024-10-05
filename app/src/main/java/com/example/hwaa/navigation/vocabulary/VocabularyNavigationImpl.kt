package com.example.hwaa.navigation.vocabulary

import com.example.hwaa.R
import com.example.hwaa.core.navigatorComponent.BaseNavigatorImpl
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@ActivityScoped
class VocabularyNavigationImpl @Inject constructor() : BaseNavigatorImpl(),
    VocabularyNavigation {
    override fun fromVocabularyToFlashCard() {
        openScreen(R.id.action_vocabListFragment_to_flashCardFragment)
    }
}