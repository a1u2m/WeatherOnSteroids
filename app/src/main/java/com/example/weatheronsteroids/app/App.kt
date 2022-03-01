package com.example.weatheronsteroids.app

import android.app.Application
import com.example.weatheronsteroids.app.dagger.AppComponent
import com.example.weatheronsteroids.app.dagger.SharedPreferencesHelperModule

class App: Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder()
            .sharedPreferencesHelperModule(SharedPreferencesHelperModule(this))
            .build()
    }
}