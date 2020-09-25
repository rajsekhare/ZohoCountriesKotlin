package com.example.zoho

import android.app.Application
import org.koin.android.ext.android.startKoin

class MainApplication : Application() {
    companion object {
         val application = MainApplication

    }
    override fun onCreate() {
        super.onCreate()
        startKoin(this,
            listOf(mainModule),
            loadPropertiesFromFile = true)
    }


}