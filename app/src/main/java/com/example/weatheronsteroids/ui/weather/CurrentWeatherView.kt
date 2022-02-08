package com.example.weatheronsteroids.ui.weather

import com.example.weatheronsteroids.model.Response
import com.example.weatheronsteroids.ui.base.BaseMvpView

interface CurrentWeatherView: BaseMvpView {

    fun fillViews(t: Response)

    fun hideProgressBar()

    fun greetUser(name: String)

}