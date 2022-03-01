package com.example.weatheronsteroids.presentation.weatherforecast

import android.content.Context
import android.util.Log
import com.example.weatheronsteroids.app.App
import com.example.weatheronsteroids.domain.SharedPreferencesHelper
import com.example.weatheronsteroids.app.dagger.AppScope
import com.example.weatheronsteroids.entity.Forecast
import com.example.weatheronsteroids.entity.Response
import com.example.weatheronsteroids.domain.RetrofitHelper
import com.example.weatheronsteroids.presentation.main.Injectable
import com.example.weatheronsteroids.domain.utils.showError
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subscribers.DisposableSubscriber
import moxy.MvpPresenter
import javax.inject.Inject

@AppScope
class WeatherForecastPresenter @Inject constructor(
    val sharedPreferencesHelper: SharedPreferencesHelper,
    val context: Context
) : MvpPresenter<WeatherForecastView>(), Injectable {

    private val TAG = "WeatherForecastPresenter"

    @Inject
    lateinit var retrofitHelper: RetrofitHelper

    private val dateList = mutableListOf<String>()
    private val dateMap = mutableMapOf<String, List<Response>>()

    init {
        (context.applicationContext as App).appComponent.inject(this)
    }

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

                        kotlin.runCatching {
                            //получаю даты
                            for (i in t.list?.indices!!) {
                                val date =
                                    transformDateToRussian(t.list!![i].dt_txt!!).substring(0, 5)
                                if (dateList.contains(date)) continue
                                dateList.add(date)
                            }

                            //сую в мапу респонсы по их датам, чтоб в каждую дату были только свои респонсы
                            var count = 0
                            for (i in dateList.indices) {
                                val responsesByDate = mutableListOf<Response>()
                                for (j in t.list!!.indices) {
                                    if (dateList[i] == transformDateToRussian(t.list!![j].dt_txt!!)
                                            .substring(0, 5)
                                    ) {
                                        responsesByDate.add(t.list!![j])
                                    }
                                }
                                dateMap[dateList[i]] = responsesByDate
                                if (count == 0) {
                                    viewState.fillViews(responsesByDate) //отправляю нужный список в фрагмент, чтобы отрисовались данные по текущей дате
                                    count++
                                }
                            }

                            viewState.fillDates(dateMap.keys)
                        }.onFailure {
                            showError(context)
                        }
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
                    viewState.setAdapter()
                    viewState.hideProgressBar()
                }
            })
    }

    fun fillViews(key: String) {
        viewState.fillViews(dateMap.getValue(key).toMutableList())
        viewState.resetAdapter()
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