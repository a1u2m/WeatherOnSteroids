package com.example.weatheronsteroids.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class AirPollutionMain {

    @SerializedName("aqi")
    @Expose
    lateinit var aqi: String

}