package com.example.weatheronsteroids.ui.settings

import android.util.Log
import com.example.weatheronsteroids.data.SharedPreferencesHelper
import com.example.weatheronsteroids.network.RetrofitHelper
import com.example.weatheronsteroids.ui.base.BaseMvpPresenter
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subscribers.DisposableSubscriber
import moxy.MvpPresenter
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class SettingsPresenter @Inject constructor(
    val sharedPreferencesHelper: SharedPreferencesHelper,
    val retrofitHelper: RetrofitHelper
) : MvpPresenter<SettingsView>() {

    private val TAG = "SettingsPresenter"

    fun humanTime(): String {
        val time = sharedPreferencesHelper.getTime()

        val hours: Int = time / 3600
        val minutes: Int = (time - (hours * 3600)) / 60
        val seconds: Int = time - hours * 3600 - minutes * 60

        val sb = StringBuilder()
        if (hours == 0) {
            sb.append("00:")
        } else {
            sb.append("$hours:")
        }
        if (minutes == 0) {
            sb.append("00:")
        } else {
            sb.append("$minutes:")
        }
        if (seconds == 0) {
            sb.append("00")
        } else {
            sb.append("$seconds")
        }
        return sb.toString()
    }

    fun getLaunchCount(): Int {
        return sharedPreferencesHelper.getLaunch()
    }

    fun setName(name: String) {
        sharedPreferencesHelper.setName(name)
    }

    fun onBecomeIncognitoClicked() {
        sharedPreferencesHelper.clearName()
        viewState.showMessage()
        Flowable
            .interval(1, TimeUnit.SECONDS)
            .take(3)
            .takeLast(1)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : DisposableSubscriber<Long>() {

                override fun onNext(t: Long?) {
                    //do nothing
                }

                override fun onComplete() {
                    viewState.hideMessage()
                }

                override fun onError(t: Throwable?) {
                    if (t != null) {
                        Log.d(TAG, "onError: ${t.message}")
                    } else {
                        Log.d(TAG, "onError: t == null")
                    }
                }
            })
    }
}