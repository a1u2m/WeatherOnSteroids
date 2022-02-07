package com.example.weatheronsteroids.ui.weatherforecast

import android.util.Log
import com.example.weatheronsteroids.data.SharedPreferencesHelper
import com.example.weatheronsteroids.model.Forecast
import com.example.weatheronsteroids.network.RetrofitHelper
import com.example.weatheronsteroids.ui.base.BaseMvpPresenter
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subscribers.DisposableSubscriber
import javax.inject.Inject

class WeatherForecastPresenter @Inject constructor(
    val sharedPreferencesHelper: SharedPreferencesHelper,
    val retrofitHelper: RetrofitHelper
) : BaseMvpPresenter<WeatherForecastView>() {

    private val TAG = "WeatherForecastPresenter"

    private val ID = "511180"
    private val API_KEY = "3767cbc63512e48175b64b1b5664d14c"
    private val LANG = "ru"
    private val UNITS = "metric"

    fun setupFlowable() {
        val flowable: Flowable<Forecast> = retrofitHelper.getApi()
            .getWeatherForecast(ID, API_KEY, LANG, UNITS)
        flowable.take(1)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : DisposableSubscriber<Forecast>() {

                override fun onNext(t: Forecast?) {
                    if (t != null) {
                        view?.fillViews(t)
                    }
                }

                override fun onError(t: Throwable?) {
                    view?.showToast()
                    if (t != null) {
                        Log.d(TAG, "onError: ${t.message}")
                    } else {
                        Log.d(TAG, "onError: t == null")
                    }
                }

                override fun onComplete() {
                    view?.setAdapter()
                    view?.hideProgressBar()
                }
            })
    }
}