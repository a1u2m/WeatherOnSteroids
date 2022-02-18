package com.example.weatheronsteroids.ui.weather

import android.content.Context
import android.util.Log
import com.example.weatheronsteroids.app.App
import com.example.weatheronsteroids.data.SharedPreferencesHelper
import com.example.weatheronsteroids.model.Response
import com.example.weatheronsteroids.network.RetrofitHelper
import com.example.weatheronsteroids.ui.main.Injectable
import com.example.weatheronsteroids.utils.showError
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subscribers.DisposableSubscriber
import moxy.MvpPresenter
import javax.inject.Inject

class CurrentWeatherPresenter @Inject constructor(
    val sharedPreferencesHelper: SharedPreferencesHelper,
    val context: Context
) : MvpPresenter<CurrentWeatherView>(), Injectable {

    private val TAG = "CurrentWeatherPresenter"

    @Inject
    lateinit var retrofitHelper: RetrofitHelper

    init {
        (context.applicationContext as App).appComponent.inject(this)
    }

    fun setupFlowable() {
        val responseFlowable: Flowable<Response> =
            retrofitHelper.getApi()
                .getCurrentWeather(
                    retrofitHelper.ID,
                    retrofitHelper.API_KEY,
                    retrofitHelper.LANG,
                    retrofitHelper.UNITS
                )

        responseFlowable.take(1)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : DisposableSubscriber<Response>() {

                override fun onNext(t: Response?) {
                    if (t != null) {
                        viewState.fillViews(t)
                    }
                }

                override fun onError(t: Throwable?) {
                    showError(context)
                    if (t != null) {
                        Log.d(TAG, "onError: ${t.message}")
                    } else {
                        Log.d(TAG, "onError: t == null")
                    }
                }

                override fun onComplete() {
                    viewState.hideProgressBar()
                }
            })
    }

    fun isCanGreet() {
        if (sharedPreferencesHelper.getIsCanGreet()) {
            sharedPreferencesHelper.getUserName()?.let { viewState.greetUser(it) }
            sharedPreferencesHelper.putIsCanGreet()
        }
    }
}