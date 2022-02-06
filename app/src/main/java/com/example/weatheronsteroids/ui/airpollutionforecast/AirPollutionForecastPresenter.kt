package com.example.weatheronsteroids.ui.airpollutionforecast

import com.example.weatheronsteroids.data.SharedPreferencesHelper
import com.example.weatheronsteroids.network.RetrofitHelper
import com.example.weatheronsteroids.ui.base.BaseMvpPresenter
import javax.inject.Inject

class AirPollutionForecastPresenter @Inject constructor(
    val sharedPreferencesHelper: SharedPreferencesHelper,
    val retrofitHelper: RetrofitHelper
): BaseMvpPresenter<AirPollutionForecastView>(){
}