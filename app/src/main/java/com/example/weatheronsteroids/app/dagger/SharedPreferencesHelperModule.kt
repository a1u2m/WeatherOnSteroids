package com.example.weatheronsteroids.app.dagger

import android.content.Context
import dagger.Module
import dagger.Provides

@Module
class SharedPreferencesHelperModule(private val context: Context) {

    @Provides
    fun getAppContext(): Context {
        return context
    }
}