package com.example.weatheronsteroids.presentation.airpollution

import android.content.Context
import android.util.Log
import com.example.weatheronsteroids.app.App
import com.example.weatheronsteroids.domain.SharedPreferencesHelper
import com.example.weatheronsteroids.entity.CurrentAirPollution
import com.example.weatheronsteroids.domain.RetrofitHelper
import com.example.weatheronsteroids.presentation.main.Injectable
import com.example.weatheronsteroids.domain.utils.showError
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subscribers.DisposableSubscriber
import moxy.MvpPresenter
import javax.inject.Inject

class AirPollutionPresenter @Inject constructor(
    val sharedPreferencesHelper: SharedPreferencesHelper,
    val context: Context
) : MvpPresenter<AirPollutionView>(), Injectable {

    @Inject
    lateinit var retrofitHelper: RetrofitHelper

    private val TAG = "AirPollutionPresenter"

    init {
        (context.applicationContext as App).appComponent.inject(this)
    }

    fun setupFlowable() {
        val flowable: Flowable<CurrentAirPollution> =
            retrofitHelper.getApi().getCurrentAirPollution(
                retrofitHelper.LAT,
                retrofitHelper.LON,
                retrofitHelper.API_KEY
            )

        flowable.take(1)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : DisposableSubscriber<CurrentAirPollution>() {

                override fun onNext(t: CurrentAirPollution?) {
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
}