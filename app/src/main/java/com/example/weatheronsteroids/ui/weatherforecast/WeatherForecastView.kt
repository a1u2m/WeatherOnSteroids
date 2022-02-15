package com.example.weatheronsteroids.ui.weatherforecast

import com.example.weatheronsteroids.model.Forecast
import com.example.weatheronsteroids.model.Response
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

interface WeatherForecastView: MvpView {

    @StateStrategyType(value = AddToEndSingleStrategy::class)
    fun fillViews(t: MutableList<Response>)

    @StateStrategyType(value = AddToEndSingleStrategy::class)
    fun fillDates(t: MutableSet<String>)

    @StateStrategyType(value = AddToEndSingleStrategy::class)
    fun hideProgressBar()

    @StateStrategyType(value = AddToEndSingleStrategy::class)
    fun setAdapter()

    @StateStrategyType(value = AddToEndSingleStrategy::class)
    fun resetAdapter()

}