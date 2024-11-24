package com.example.hwaa.presentation.navigation.vocabulary

import com.example.hwaa.presentation.core.navigatorComponent.BaseNavigator

interface VocabularyNavigation : BaseNavigator {
    fun fromVocabularyToFlashCard()
    fun fromVocabularyToTest()
}