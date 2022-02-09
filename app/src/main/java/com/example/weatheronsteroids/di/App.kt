package com.example.weatheronsteroids.di

import android.app.Application
import com.example.weatheronsteroids.di.sharedpreferences.SharedPreferencesHelperModule
import com.github.terrakok.cicerone.Cicerone

class App: Application() {

    lateinit var appComponent: AppComponent

    private val cicerone = Cicerone.create()
    val router get() = cicerone.router
    val navigatorHolder get() = cicerone.getNavigatorHolder()

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
        appComponent = DaggerAppComponent
            .builder()
            .sharedPreferencesHelperModule(SharedPreferencesHelperModule(this))
            .build()
    }

    companion object {
        internal lateinit var INSTANCE: App
            private set
    }



}