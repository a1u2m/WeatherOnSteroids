package com.example.weatheronsteroids.ui.weather

import android.util.Log
import com.example.weatheronsteroids.data.SharedPreferencesHelper
import com.example.weatheronsteroids.model.Response
import com.example.weatheronsteroids.network.RetrofitHelper
import com.example.weatheronsteroids.ui.base.BaseMvpPresenter
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subscribers.DisposableSubscriber
import javax.inject.Inject

class CurrentWeatherPresenter @Inject constructor(
    val sharedPreferencesHelper: SharedPreferencesHelper,
    val retrofitHelper: RetrofitHelper
): BaseMvpPresenter<CurrentWeatherView>() {

    private val TAG = "CurrentWeatherPresenter"

    private val ID = "511180"
    private val API_KEY = "3767cbc63512e48175b64b1b5664d14c"
    private val LANG = "ru"
    private val UNITS = "metric"



    fun setupFlowable() {
        val responseFlowable: Flowable<Response> =
            retrofitHelper.getApi()
                .getCurrentWeather(ID, API_KEY, LANG, UNITS)

        responseFlowable.take(1)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : DisposableSubscriber<Response>() {

                override fun onNext(t: Response?) {
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
                    view?.hideProgressBar()
                }
            })
    }

    fun isCanGreet() {
        if (sharedPreferencesHelper.getIsCanGreet()) {
            sharedPreferencesHelper.getUserName()?.let { view?.greetUser(it) }
            sharedPreferencesHelper.putIsCanGreet()
        }
    }
}