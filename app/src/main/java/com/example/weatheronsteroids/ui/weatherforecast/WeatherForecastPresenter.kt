package com.example.weatheronsteroids.ui.weatherforecast

import android.util.Log
import com.example.weatheronsteroids.data.SharedPreferencesHelper
import com.example.weatheronsteroids.model.Forecast
import com.example.weatheronsteroids.model.Response
import com.example.weatheronsteroids.network.RetrofitHelper
import com.example.weatheronsteroids.utils.ToastHelper
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

    private val dateList = mutableListOf<String>()
    private val dateMap = mutableMapOf<String, List<Response>>()

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

                        //пошла жара

                        //получаю даты
                        for (i in t.response.indices) {
                            val date = transformDateToRussian(t.response[i].dt_txt).substring(0, 5)
                            if (dateList.contains(date)) continue
                            dateList.add(date)
                        }

                        //сую в мапу респонсы по их датам, чтоб в каждую дату были только свои респонсы

                        for (i in dateList.indices) {
                            val responsesByDate = mutableListOf<Response>()
                            for (j in t.response.indices) {
                                if (dateList[i] == transformDateToRussian(t.response[j].dt_txt).substring(0, 5)) {
                                    responsesByDate.add(t.response[j])
                                }
                            }
                            dateMap[dateList[i]] = responsesByDate
                        }

                        transformDateToRussian(t.response[0].dt_txt)
                        transformDateToRussian(t.response[1].dt_txt)
                        transformDateToRussian(t.response[39].dt_txt)
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

    companion object {
        fun transformDateToRussian(string: String): String {
            val sb = StringBuilder()
            val date = string.substring(8, 10)
            val month = string.substring(5, 7)
            val year = string.substring(0, 4)
            val time = string.substring(11)
            sb.append(date)
            sb.append("-")
            sb.append(month)
            sb.append("-")
            sb.append(year)
            sb.append(" ")
            sb.append(time)
            return sb.toString()
        }
    }
}