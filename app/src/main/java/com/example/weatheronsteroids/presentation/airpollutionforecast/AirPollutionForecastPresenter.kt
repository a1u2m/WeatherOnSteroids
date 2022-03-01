package com.example.weatheronsteroids.presentation.airpollutionforecast

import android.content.Context
import android.util.Log
import com.example.weatheronsteroids.app.App
import com.example.weatheronsteroids.domain.SharedPreferencesHelper
import com.example.weatheronsteroids.app.dagger.AppScope
import com.example.weatheronsteroids.entity.AirQualityAndComponents
import com.example.weatheronsteroids.entity.CurrentAirPollution
import com.example.weatheronsteroids.domain.RetrofitHelper
import com.example.weatheronsteroids.presentation.main.Injectable
import com.example.weatheronsteroids.domain.utils.showError
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subscribers.DisposableSubscriber
import moxy.MvpPresenter
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@AppScope
class AirPollutionForecastPresenter @Inject constructor(
    val sharedPreferencesHelper: SharedPreferencesHelper,
    val context: Context
) : MvpPresenter<AirPollutionForecastView>(), Injectable {

    private val TAG = "AirPollutionForecastAdapter"

    @Inject
    lateinit var retrofitHelper: RetrofitHelper

    private val dateList = mutableListOf<String>()
    private val dateMap = mutableMapOf<String, List<AirQualityAndComponents>>()

    init {
        (context.applicationContext as App).appComponent.inject(this)
    }

    fun setupFlowable() {
        val flowable: Flowable<CurrentAirPollution> =
            retrofitHelper.getApi().getAirPollutionForecast(
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

                        kotlin.runCatching {
                            //получаю даты
                            for (i in t.list?.indices!!) {
                                val date = epochToDate(t.list!![i].dt!!)
                                    .substring(0, 5)
                                if (dateList.contains(date)) continue
                                dateList.add(date)
                            }

                            //сую в мапу респонсы по их датам, чтоб в каждую дату были только свои респонсы
                            var count = 0
                            for (i in dateList.indices) {
                                val responsesByDate = mutableListOf<AirQualityAndComponents>()
                                for (j in t.list?.indices!!) {
                                    if (dateList[i] == epochToDate(t.list!![j].dt!!)
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
        private fun epochToDate(string: String): String {
            val sdf = SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault())
            val netDate = Date(string.toLong() * 1000)
            return sdf.format(netDate)
        }
    }
}