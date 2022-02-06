package com.example.weatheronsteroids.di

import com.example.weatheronsteroids.di.sharedpreferences.SharedPreferencesHelperModule
import com.example.weatheronsteroids.ui.main.MainPresenter
import com.example.weatheronsteroids.ui.weather.CurrentWeatherPresenter
import dagger.Component

@Component (modules = [SharedPreferencesHelperModule::class])
interface AppComponent {

    fun getMainActivityPresenter(): MainPresenter

    fun getCurrentWeatherPresenter(): CurrentWeatherPresenter

}