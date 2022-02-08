package com.example.weatheronsteroids.ui.settings

import com.example.weatheronsteroids.ui.base.BaseMvpView
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

interface SettingsView: MvpView {

    @StateStrategyType(value = AddToEndSingleStrategy::class)
    fun showMessage()

    @StateStrategyType(value = AddToEndSingleStrategy::class)
    fun hideMessage()

}