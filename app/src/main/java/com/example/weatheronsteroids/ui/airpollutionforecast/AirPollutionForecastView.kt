package com.example.weatheronsteroids.ui.airpollutionforecast

import com.example.weatheronsteroids.model.AirQualityAndComponents
import com.example.weatheronsteroids.model.CurrentAirPollution
import com.example.weatheronsteroids.model.Response
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

interface AirPollutionForecastView : MvpView {

    @StateStrategyType(value = AddToEndSingleStrategy::class)
    fun fillViews(t: MutableList<AirQualityAndComponents>)

    @StateStrategyType(value = AddToEndSingleStrategy::class)
    fun fillDates(t: MutableSet<String>)

    @StateStrategyType(value = AddToEndSingleStrategy::class)
    fun hideProgressBar()

    @StateStrategyType(value = AddToEndSingleStrategy::class)
    fun setAdapter()

    @StateStrategyType(value = AddToEndSingleStrategy::class)
    fun resetAdapter()

}