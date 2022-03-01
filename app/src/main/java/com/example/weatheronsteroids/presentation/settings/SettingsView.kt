package com.example.weatheronsteroids.presentation.settings

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

interface SettingsView: MvpView {

    @StateStrategyType(value = AddToEndSingleStrategy::class)
    fun showMessage()

    @StateStrategyType(value = AddToEndSingleStrategy::class)
    fun hideMessage()

}