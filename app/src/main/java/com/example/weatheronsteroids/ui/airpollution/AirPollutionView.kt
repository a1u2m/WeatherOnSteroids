package com.example.weatheronsteroids.ui.airpollution

import com.example.weatheronsteroids.model.CurrentAirPollution
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

interface AirPollutionView : MvpView {

    @StateStrategyType(value = AddToEndSingleStrategy::class)
    fun fillViews(t: CurrentAirPollution)

    @StateStrategyType(value = AddToEndSingleStrategy::class)
    fun hideProgressBar()

}