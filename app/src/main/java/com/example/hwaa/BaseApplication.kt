package com.example.hwaa

import android.app.Application
import com.example.hwaa.presentation.util.UserProvider
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import timber.log.Timber.Forest.plant
import javax.inject.Inject


@HiltAndroidApp
class BaseApplication @Inject constructor() : Application() {
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            plant(Timber.DebugTree())
        }
        UserProvider.init(this)
    }
}