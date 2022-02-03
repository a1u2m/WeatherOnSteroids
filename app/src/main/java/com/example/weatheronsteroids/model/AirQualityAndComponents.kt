package com.example.weatheronsteroids.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class AirQualityAndComponents {

    @SerializedName("main")
    @Expose
    lateinit var airPollutionMain: AirPollutionMain

    @SerializedName("components")
    @Expose
    lateinit var airPollutionComponents: AirPollutionComponents

}