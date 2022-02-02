package com.example.weatheronsteroids.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Forecast {

    @SerializedName("list")
    @Expose
    lateinit var response: List<Response>

}