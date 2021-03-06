package com.example.weatheronsteroids.entity

import com.example.weatheronsteroids.entity.CurrentAirPollution
import com.example.weatheronsteroids.entity.Forecast
import com.example.weatheronsteroids.entity.Response
import io.reactivex.rxjava3.core.Flowable
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenWeatherMapApi {

    @GET("/data/2.5/weather?")
    fun getCurrentWeather(
        @Query("id") key: String,
        @Query("appid") appid: String,
        @Query("lang") lang: String,
        @Query("units") units: String
    ): Flowable<Response>

    @GET("/data/2.5/forecast?")
    fun getWeatherForecast(
        @Query("id") key: String,
        @Query("appid") appid: String,
        @Query("lang") lang: String,
        @Query("units") units: String
    ): Flowable<Forecast>

    @GET("/data/2.5/air_pollution?")
    fun getCurrentAirPollution(
        @Query("lat") lat: String,
        @Query("lon") lon: String,
        @Query("appid") appid: String
    ): Flowable<CurrentAirPollution>

    @GET("/data/2.5/air_pollution/forecast?")
    fun getAirPollutionForecast(
        @Query("lat") lat: String,
        @Query("lon") lon: String,
        @Query("appid") appid: String
    ): Flowable<CurrentAirPollution>

}