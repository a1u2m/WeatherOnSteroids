package com.example.weatheronsteroids.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class CurrentAirPollution {

    @SerializedName("list")
    @Expose
    lateinit var airQualityAndComponents: List<AirQualityAndComponents>

}