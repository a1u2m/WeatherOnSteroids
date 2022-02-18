package com.example.weatheronsteroids.di

import com.example.weatheronsteroids.di.sharedpreferences.SharedPreferencesHelperModule
import com.example.weatheronsteroids.ui.airpollution.AirPollutionFragment
import com.example.weatheronsteroids.ui.airpollutionforecast.AirDateAdapter
import com.example.weatheronsteroids.ui.airpollutionforecast.AirPollutionForecastFragment
import com.example.weatheronsteroids.ui.main.MainActivity
import com.example.weatheronsteroids.ui.settings.SettingsFragment
import com.example.weatheronsteroids.ui.weather.CurrentWeatherFragment
import com.example.weatheronsteroids.ui.weatherforecast.WeatherDateAdapter
import com.example.weatheronsteroids.ui.weatherforecast.WeatherForecastFragment
import dagger.Component

@AppScope
@Component(
    modules = [
        SharedPreferencesHelperModule::class]
)
interface AppComponent {

    fun inject(fragment: AirPollutionFragment)

    fun inject(fragment: AirPollutionForecastFragment)

    fun inject(fragment: SettingsFragment)

    fun inject(fragment: WeatherForecastFragment)

    fun inject(fragment: CurrentWeatherFragment)

    fun inject(activity: MainActivity)

    fun inject(adapter: AirDateAdapter)

    fun inject(adapter: WeatherDateAdapter)



}