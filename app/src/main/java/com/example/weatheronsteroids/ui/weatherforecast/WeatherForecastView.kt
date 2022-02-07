package com.example.weatheronsteroids.ui.weatherforecast

import com.example.weatheronsteroids.model.Forecast
import com.example.weatheronsteroids.ui.base.BaseMvpView

interface WeatherForecastView: BaseMvpView {

    fun fillViews(t: Forecast)

    fun showToast()

    fun hideProgressBar()

    fun setAdapter()

}