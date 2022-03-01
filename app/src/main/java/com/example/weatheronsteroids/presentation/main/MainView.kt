package com.example.weatheronsteroids.presentation.main

import androidx.fragment.app.Fragment
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

interface MainView: MvpView {

    @StateStrategyType(value = AddToEndSingleStrategy::class)
    fun countLaunch()

    @StateStrategyType(value = AddToEndSingleStrategy::class)
    fun init()

    @StateStrategyType(value = AddToEndSingleStrategy::class)
    fun showFragment(fragment: Fragment, id: Int)

}