package com.example.weatheronsteroids.ui.airpollution

import android.util.Log
import com.example.weatheronsteroids.data.SharedPreferencesHelper
import com.example.weatheronsteroids.model.CurrentAirPollution
import com.example.weatheronsteroids.network.RetrofitHelper
import com.example.weatheronsteroids.ui.base.BaseMvpPresenter
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subscribers.DisposableSubscriber
import javax.inject.Inject

class AirPollutionPresenter @Inject constructor(
    val sharedPreferencesHelper: SharedPreferencesHelper,
    val retrofitHelper: RetrofitHelper
) : BaseMvpPresenter<AirPollutionView>() {

    private val TAG = "AirPollutionPresenter"

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
}