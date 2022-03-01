package com.example.weatheronsteroids.entity

data class AirQualityAndComponents(
    var main: AirPollutionMain? = null,
    var components: AirPollutionComponents? = null,
    var dt: String? = null
)