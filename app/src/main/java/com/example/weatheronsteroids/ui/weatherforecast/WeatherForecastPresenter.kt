package com.example.weatheronsteroids.ui.weatherforecast

import com.example.weatheronsteroids.data.SharedPreferencesHelper
import com.example.weatheronsteroids.network.RetrofitHelper
import com.example.weatheronsteroids.ui.base.BaseMvpPresenter
import javax.inject.Inject

class WeatherForecastPresenter @Inject constructor(
    val sharedPreferencesHelper: SharedPreferencesHelper,
    val retrofitHelper: RetrofitHelper
) : BaseMvpPresenter<WeatherForecastView>() {
}