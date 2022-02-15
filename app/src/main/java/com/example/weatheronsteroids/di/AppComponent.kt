package com.example.weatheronsteroids.di

import com.example.weatheronsteroids.di.sharedpreferences.SharedPreferencesHelperModule
import com.example.weatheronsteroids.ui.airpollution.AirPollutionPresenter
import com.example.weatheronsteroids.ui.airpollutionforecast.AirPollutionForecastPresenter
import com.example.weatheronsteroids.ui.main.MainPresenter
import com.example.weatheronsteroids.ui.settings.SettingsPresenter
import com.example.weatheronsteroids.ui.weather.CurrentWeatherPresenter
import com.example.weatheronsteroids.ui.weatherforecast.WeatherForecastPresenter
import dagger.Component

@AppScope
@Component(
    modules = [
        SharedPreferencesHelperModule::class]
)
interface AppComponent {

    fun getMainActivityPresenter(): MainPresenter

    fun getCurrentWeatherPresenter(): CurrentWeatherPresenter

    fun getWeatherForecastPresenter(): WeatherForecastPresenter

    fun getAirPollutionForecastPresenter(): AirPollutionForecastPresenter

    fun getAirPollutionPresenter(): AirPollutionPresenter

    fun getSettingsPresenter(): SettingsPresenter

}