package com.example.weatheronsteroids.entity

import io.reactivex.rxjava3.core.Flowable
import java.util.concurrent.Flow

interface WeatherRepository {

    fun getCurrentWeather(): Flowable<Response>

    fun getWeatherForecast(): Flowable<Forecast>

    fun getCurrentAirPollution(): Flowable<CurrentAirPollution>

    fun getAirPollutionForecast(): Flowable<CurrentAirPollution>
}