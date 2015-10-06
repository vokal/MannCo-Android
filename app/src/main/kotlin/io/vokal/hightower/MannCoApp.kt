package io.vokal.hightower

import android.app.Application
import timber.log.Timber

class MannCoApp : Application() {

    override fun onCreate() {
        super.onCreate()

        Timber.uprootAll()
        Timber.plant(Timber.DebugTree())
    }
}