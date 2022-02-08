package com.example.weatheronsteroids.ui.weatherforecast

import android.util.Log
import com.example.weatheronsteroids.data.SharedPreferencesHelper
import com.example.weatheronsteroids.model.Forecast
import com.example.weatheronsteroids.network.RetrofitHelper
import com.example.weatheronsteroids.ui.base.BaseMvpPresenter
import com.example.weatheronsteroids.utils.secrettextview.ToastHelper
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subscribers.DisposableSubscriber
import moxy.MvpPresenter
import javax.inject.Inject

class WeatherForecastPresenter @Inject constructor(
    val sharedPreferencesHelper: SharedPreferencesHelper,
    val retrofitHelper: RetrofitHelper,
    val toastHelper: ToastHelper
) : MvpPresenter<WeatherForecastView>() {

    private val TAG = "WeatherForecastPresenter"

    fun setupFlowable() {
        val flowable: Flowable<Forecast> = retrofitHelper.getApi()
            .getWeatherForecast(
                retrofitHelper.ID,
                retrofitHelper.API_KEY,
                retrofitHelper.LANG,
                retrofitHelper.UNITS
            )
        flowable.take(1)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : DisposableSubscriber<Forecast>() {

                override fun onNext(t: Forecast?) {
                    if (t != null) {
                        viewState.fillViews(t)
                    }
                }

                override fun onError(t: Throwable?) {
                    toastHelper.showErrorToast()
                    if (t != null) {
                        Log.d(TAG, "onError: ${t.message}")
                    } else {
                        Log.d(TAG, "onError: t == null")
                    }
                }

                override fun onComplete() {
                    viewState.setAdapter()
                    viewState.hideProgressBar()
                }
            })
    }
}