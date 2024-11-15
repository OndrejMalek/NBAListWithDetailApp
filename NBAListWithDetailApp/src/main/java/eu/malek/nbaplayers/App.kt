package eu.malek.nbaplayers

import android.app.Application

class App : Application() {
    lateinit var appModule: AppModule

    override fun onCreate() {
        super.onCreate()

        appModule = AppModule(applicationContext.cacheDir)
    }
}