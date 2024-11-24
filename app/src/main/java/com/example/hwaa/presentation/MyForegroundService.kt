package com.example.hwaa.presentation

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.os.SystemClock
import androidx.core.app.NotificationCompat
import com.example.hwaa.R
import com.example.hwaa.data.model.WordStatModel
import com.example.hwaa.domain.Response
import com.example.hwaa.domain.usecase.learning.GetPushWordStatUseCase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject
import kotlin.random.Random

@AndroidEntryPoint
class MyForegroundService : Service() {

    private lateinit var handler: Handler
    private lateinit var runnable: Runnable
    private val interval: Long = 60000*5// 10 minutes in milliseconds


    @Inject
    lateinit var getPushWordStatUseCase: GetPushWordStatUseCase

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        startForegroundService()
        return START_STICKY
    }

//    override fun onTaskRemoved(rootIntent: Intent?) {
//        val restartServiceIntent =
//            Intent(applicationContext, MyForegroundService::class.java).apply {
//                setPackage(packageName)
//            }
//        val restartPendingIntent = PendingIntent.getService(
//            applicationContext,
//            1,
//            restartServiceIntent,
//            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
//        )
//        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
//        alarmManager.set(
//            AlarmManager.ELAPSED_REALTIME,
//            SystemClock.elapsedRealtime() + 1000, // Restart after 1 second
//            restartPendingIntent
//        )
//        super.onTaskRemoved(rootIntent)
//    }


    override fun onCreate() {
        super.onCreate()
        handler = Handler(Looper.getMainLooper())
    }

    private fun startForegroundService() {

        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Word Reminder")
            .setContentText("Remember to learn a new word")
            .setSmallIcon(R.drawable.ic_camera)
            .build()

        startForeground(1, notification)

        runnable = Runnable {
            callApiAndSendNotification()
            handler.postDelayed(runnable, interval)
        }
        handler.post(runnable)
    }

    private fun callApiAndSendNotification() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                getPushWordStatUseCase.invoke().collectLatest {
                    when (it) {
                        is Response.Success -> {
                            (it.data)
                            sendNotification(it.data)
                        }

                        is Response.Error -> {
                            Timber.e("Error: ${it.exception}")
                        }
                    }
                }
            } catch (e: Exception) {
                Timber.e("Error: $e")
            }
        }
    }

    private fun sendNotification(wordStatModel: WordStatModel) {
        val context = applicationContext
        val notificationId = Random.nextInt()

        val rememberIntent = Intent(context, NotificationActionReceiver::class.java).apply {
            action = "ACTION_REMEMBER"
            putExtra("wordId", wordStatModel.word.id)
            putExtra("score", wordStatModel.score)
            putExtra("NOTIFICATION_ID", notificationId)
        }

        val forgetIntent = Intent(context, NotificationActionReceiver::class.java).apply {
            action = "ACTION_FORGET"
            putExtra("wordId", wordStatModel.word.id)
            putExtra("score", wordStatModel.score)
            putExtra("NOTIFICATION_ID", notificationId)
        }

        val rememberPendingIntent = PendingIntent.getBroadcast(
            context,
            0,
            rememberIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val forgetPendingIntent = PendingIntent.getBroadcast(
            context,
            1,
            forgetIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        // Build the notification
        val notificationBuilder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_camera) // Replace with your icon
            .setContentTitle("Word Reminder")
            .setContentText("Remember to learn ${wordStatModel.word.hanzi}")
            .addAction(R.drawable.ic_right, "Remember", rememberPendingIntent)
            .addAction(R.drawable.ic_left, "Forget", forgetPendingIntent)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)

        val notificationManager = context.getSystemService(NotificationManager::class.java)

        notificationManager.notify(notificationId, notificationBuilder.build())
    }


    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacks(runnable)
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    companion object {
        const val CHANNEL_ID = "foreground_service_channel"
    }
}
