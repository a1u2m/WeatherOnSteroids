package com.example.weatheronsteroids.model

data class AirQualityAndComponents(
    var main: AirPollutionMain? = null,
    var components: AirPollutionComponents? = null,
    var dt: String? = null
)