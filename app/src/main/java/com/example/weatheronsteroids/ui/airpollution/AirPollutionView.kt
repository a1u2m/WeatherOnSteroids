package com.example.weatheronsteroids.ui.airpollution

import com.example.weatheronsteroids.model.CurrentAirPollution
import com.example.weatheronsteroids.ui.base.BaseMvpView

interface AirPollutionView : BaseMvpView {

    fun fillViews(t: CurrentAirPollution)

    fun hideProgressBar()

}