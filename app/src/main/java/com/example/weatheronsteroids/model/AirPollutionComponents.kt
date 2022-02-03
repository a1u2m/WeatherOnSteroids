package com.example.weatheronsteroids.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class AirPollutionComponents {

    @SerializedName("co")
    @Expose
    lateinit var co: String

    @SerializedName("no")
    @Expose
    lateinit var no: String

    @SerializedName("no2")
    @Expose
    lateinit var no2: String

    @SerializedName("o3")
    @Expose
    lateinit var o3: String

    @SerializedName("so2")
    @Expose
    lateinit var so2: String

    @SerializedName("pm2_5")
    @Expose
    lateinit var pm2_5: String

    @SerializedName("pm10")
    @Expose
    lateinit var pm10: String

    @SerializedName("nh3")
    @Expose
    lateinit var nh3: String

}