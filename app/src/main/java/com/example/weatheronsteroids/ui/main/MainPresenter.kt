package com.example.weatheronsteroids.ui.main

import android.util.Log
import android.view.MenuItem
import androidx.fragment.app.Fragment
import com.example.weatheronsteroids.R
import com.example.weatheronsteroids.data.SharedPreferencesHelper
import com.example.weatheronsteroids.network.RetrofitHelper
import com.example.weatheronsteroids.ui.airpollution.AirPollutionFragment
import com.example.weatheronsteroids.ui.airpollutionforecast.AirPollutionForecastFragment
import com.example.weatheronsteroids.ui.base.BaseMvpPresenter
import com.example.weatheronsteroids.ui.settings.SettingsFragment
import com.example.weatheronsteroids.ui.weather.CurrentWeatherFragment
import com.example.weatheronsteroids.ui.weatherforecast.WeatherForecastFragment
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.observers.DisposableObserver
import java.util.concurrent.TimeUnit
import javax.inject.Inject

open class MainPresenter @Inject constructor(
    val sharedPreferencesHelper: SharedPreferencesHelper,
    val retrofitHelper: RetrofitHelper
) : BaseMvpPresenter<MainView>() {

    private val TAG = "MainPresenter"

    private val timeCountObservable = Observable.interval(1, TimeUnit.SECONDS)
    lateinit var timeCountDisposable: DisposableObserver<Long>

    open var isCanGreet = true

    var timeCount = 0

    fun countTime() {
        var tempTimeCount = sharedPreferencesHelper.getTime()
        tempTimeCount += timeCount
        sharedPreferencesHelper.putTime(tempTimeCount)
        timeCountDisposable.dispose()
    }

    fun createNewDisposableAndSubscribe() {
        timeCountDisposable = object : DisposableObserver<Long>() {

            override fun onNext(t: Long?) {
                timeCount++
            }

            override fun onError(t: Throwable?) {
                Log.d(TAG, "timeCount onError: ${t?.message}")
            }

            override fun onComplete() {
                //do nothing
            }
        }
        timeCountObservable.subscribe(timeCountDisposable)
    }

    fun putLaunch() {
        sharedPreferencesHelper.putLaunch()
    }
}