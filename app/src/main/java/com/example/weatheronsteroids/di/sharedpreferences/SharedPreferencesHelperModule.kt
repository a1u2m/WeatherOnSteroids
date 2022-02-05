package com.example.weatheronsteroids.di.sharedpreferences

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