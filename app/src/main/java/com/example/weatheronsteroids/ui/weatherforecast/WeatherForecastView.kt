package com.example.weatheronsteroids.ui.weatherforecast

import com.example.weatheronsteroids.model.Forecast
import com.example.weatheronsteroids.ui.base.BaseMvpView
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

interface WeatherForecastView: MvpView {

    @StateStrategyType(value = AddToEndSingleStrategy::class)
    fun fillViews(t: Forecast)

    @StateStrategyType(value = AddToEndSingleStrategy::class)
    fun hideProgressBar()

    @StateStrategyType(value = AddToEndSingleStrategy::class)
    fun setAdapter()

}