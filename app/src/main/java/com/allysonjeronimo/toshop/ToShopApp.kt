package com.allysonjeronimo.toshop

import android.app.Application
import com.allysonjeronimo.toshop.di.modelModule
import com.allysonjeronimo.toshop.di.viewModule
import org.koin.android.ext.android.startKoin
import org.koin.standalone.StandAloneContext.stopKoin

class ToShopApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin(this, listOf(viewModule, modelModule))
    }

    override fun onTerminate() {
        super.onTerminate()
        stopKoin()
    }
}