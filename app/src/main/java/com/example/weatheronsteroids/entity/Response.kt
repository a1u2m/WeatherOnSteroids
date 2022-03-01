package com.example.weatheronsteroids.entity

data class Response(
    var weather: List<Weather>? = null,
    var main: Main? = null,
    var wind: Wind? = null,
    var dt_txt: String? = null
)