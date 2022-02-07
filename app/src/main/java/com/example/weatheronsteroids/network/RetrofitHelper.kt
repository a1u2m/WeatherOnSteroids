package com.example.weatheronsteroids.network

import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

class RetrofitHelper @Inject constructor(){

    val LAT = "58.0174"
    val LON = "56.2855"
    val API_KEY = "3767cbc63512e48175b64b1b5664d14c"
    val ID = "511180"
    val LANG = "ru"
    val UNITS = "metric"

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .baseUrl("https://api.openweathermap.org")
        .build()

    private val api = retrofit.create(OpenWeatherMapApi::class.java)

    fun getApi(): OpenWeatherMapApi {
        return api
    }
}