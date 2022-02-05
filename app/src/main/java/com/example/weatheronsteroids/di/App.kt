package com.example.weatheronsteroids.di

import android.app.Application
import com.example.weatheronsteroids.di.sharedpreferences.SharedPreferencesHelperModule
import com.example.weatheronsteroids.ui.main.MainActivity

class App: Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent
            .builder()
            .sharedPreferencesHelperModule(SharedPreferencesHelperModule(this))
            .build()
    }

}