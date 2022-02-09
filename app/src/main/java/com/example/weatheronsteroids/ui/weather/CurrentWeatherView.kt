package com.example.weatheronsteroids.ui.weather

import com.example.weatheronsteroids.model.Response
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

interface CurrentWeatherView: MvpView {

    @StateStrategyType(value = AddToEndSingleStrategy::class)
    fun fillViews(t: Response)

    @StateStrategyType(value = AddToEndSingleStrategy::class)
    fun hideProgressBar()

    @StateStrategyType(value = AddToEndSingleStrategy::class)
    fun greetUser(name: String)

}