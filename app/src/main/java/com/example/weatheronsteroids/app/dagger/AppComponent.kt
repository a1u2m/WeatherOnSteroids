package com.example.weatheronsteroids.app.dagger

import com.example.weatheronsteroids.presentation.airpollution.AirPollutionFragment
import com.example.weatheronsteroids.presentation.airpollutionforecast.AirDateAdapter
import com.example.weatheronsteroids.presentation.airpollutionforecast.AirPollutionForecastFragment
import com.example.weatheronsteroids.presentation.main.Injectable
import com.example.weatheronsteroids.presentation.main.MainActivity
import com.example.weatheronsteroids.presentation.settings.SettingsFragment
import com.example.weatheronsteroids.presentation.weather.CurrentWeatherFragment
import com.example.weatheronsteroids.presentation.weatherforecast.WeatherDateAdapter
import com.example.weatheronsteroids.presentation.weatherforecast.WeatherForecastFragment
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

    fun inject(presenter: Injectable)

}