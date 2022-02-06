package com.example.weatheronsteroids.ui.airpollution

import com.example.weatheronsteroids.data.SharedPreferencesHelper
import com.example.weatheronsteroids.network.RetrofitHelper
import com.example.weatheronsteroids.ui.base.BaseMvpPresenter
import com.example.weatheronsteroids.ui.weather.CurrentWeatherView
import javax.inject.Inject

class AirPollutionPresenter @Inject constructor(
    val sharedPreferencesHelper: SharedPreferencesHelper,
    val retrofitHelper: RetrofitHelper
): BaseMvpPresenter<AirPollutionView>() {
}