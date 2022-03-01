package com.example.weatheronsteroids.presentation.weather

import com.example.weatheronsteroids.entity.Response
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