package com.example.hwaa.presentation

import android.annotation.SuppressLint
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.hwaa.domain.Response
import com.example.hwaa.domain.usecase.learning.UpdateWordStatUseCase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class NotificationActionReceiver : BroadcastReceiver() {

    @Inject
    lateinit var updateWordStatUseCase: UpdateWordStatUseCase

    @SuppressLint("NewApi")
    override fun onReceive(context: Context?, intent: Intent?) {
        if (context != null && intent != null) {
            val action = intent.action
            val wordId = intent.getStringExtra("wordId")
            val score = intent.getIntExtra("score", 0)
            val NOTIFICATION_ID = intent.getIntExtra("NOTIFICATION_ID", 0)

            val notificationManager = context.getSystemService(NotificationManager::class.java)

            when (action) {
                "ACTION_REMEMBER" -> {
                    updateWordStatScore(wordId, score + 1, true)
                    notificationManager.cancel(NOTIFICATION_ID)
                }

                "ACTION_FORGET" -> {
                    updateWordStatScore(wordId, score - 1, false)
                    notificationManager.cancel(NOTIFICATION_ID)
                }
            }
        }
    }

    private fun updateWordStatScore(wordId: String?, score: Int, isRemember: Boolean) {
        CoroutineScope(Dispatchers.IO).launch {
            if (wordId != null) {
                updateWordStatUseCase.invoke(wordId, score, isRemember).collect { response ->
                    when (response) {
                        is Response.Success -> {
                        }

                        is Response.Error -> {
                        }
                    }
                }
            }
        }
    }
}