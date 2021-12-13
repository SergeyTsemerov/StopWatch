package com.sergeytsemerov.stopwatch

import android.app.Application
import org.koin.core.context.startKoin

class StopwatchApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(mainViewModel)
        }
    }
}