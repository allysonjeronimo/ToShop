package com.allysonjeronimo.toshop

import android.app.Application
import org.koin.android.ext.android.startKoin
import org.koin.standalone.StandAloneContext.stopKoin
import com.allysonjeronimo.toshop.data.di.dataModule

class ToShopApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin(
            this,
            listOf(
                dataModule
            )
        )
    }

    override fun onTerminate() {
        super.onTerminate()
        stopKoin()
    }
}