package com.example.weatheronsteroids.presentation.airpollutionforecast

import com.example.weatheronsteroids.entity.AirQualityAndComponents
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