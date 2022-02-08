package com.example.weatheronsteroids.ui.main

import androidx.fragment.app.Fragment
import com.example.weatheronsteroids.ui.base.BaseMvpView
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