package com.example.weatheronsteroids.ui.airpollutionforecast

import com.example.weatheronsteroids.model.CurrentAirPollution
import com.example.weatheronsteroids.ui.base.BaseMvpView

interface AirPollutionForecastView : BaseMvpView {

    fun fillViews(t: CurrentAirPollution)

    fun hideProgressBar()

    fun setAdapter()

}