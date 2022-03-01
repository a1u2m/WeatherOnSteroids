package com.example.weatheronsteroids.entity.impl

import com.example.weatheronsteroids.domain.RetrofitHelper
import com.example.weatheronsteroids.entity.*
import io.reactivex.rxjava3.core.Flowable
import javax.inject.Inject

class WeatherRepositoryImpl(private val api: OpenWeatherMapApi): WeatherRepository {

    @Inject
    lateinit var retrofitHelper: RetrofitHelper

    override fun getCurrentWeather(): Flowable<Response> {
        return api.getCurrentWeather(
            retrofitHelper.ID,
            retrofitHelper.API_KEY,
            retrofitHelper.LANG,
            retrofitHelper.UNITS
        )
    }

    override fun getWeatherForecast(): Flowable<Forecast> {
        TODO("Not yet implemented")
    }

    override fun getCurrentAirPollution(): Flowable<CurrentAirPollution> {
        TODO("Not yet implemented")
    }

    override fun getAirPollutionForecast(): Flowable<CurrentAirPollution> {
        TODO("Not yet implemented")
    }
}