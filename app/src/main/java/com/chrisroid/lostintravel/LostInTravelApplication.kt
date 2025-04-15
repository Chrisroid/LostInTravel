package com.chrisroid.lostintravel

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class LostInTravelApplication : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}