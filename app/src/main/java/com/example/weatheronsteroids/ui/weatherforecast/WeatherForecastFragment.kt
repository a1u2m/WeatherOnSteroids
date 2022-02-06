package com.example.weatheronsteroids.ui.weatherforecast

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.example.weatheronsteroids.R
import com.example.weatheronsteroids.model.Forecast
import com.example.weatheronsteroids.model.Response
import com.example.weatheronsteroids.ui.main.MainActivity
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subscribers.DisposableSubscriber

class WeatherForecastFragment : Fragment(), WeatherForecastView {

    private val TAG = "WeatherForecastFragment"

    private val ID = "511180"
    private val API_KEY = "3767cbc63512e48175b64b1b5664d14c"
    private val LANG = "ru"
    private val UNITS = "metric"

    lateinit var recycler: RecyclerView
    lateinit var loading: AppCompatTextView
    lateinit var progressBar: ProgressBar

    private val forecastList = mutableListOf<Forecast>()
    private var responseList = listOf<Response>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_weather_forecast, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initViews()

        val responseFlowable: Flowable<Forecast> =
            (activity as MainActivity).presenter.retrofitHelper.getApi()
                .getWeatherForecast(ID, API_KEY, LANG, UNITS)
        setupFlowable(responseFlowable)
    }

    private fun setupFlowable(flowable: Flowable<Forecast>) {
        flowable.take(1)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : DisposableSubscriber<Forecast>() {

                override fun onNext(t: Forecast?) {
                    t?.let { forecastList.add(it) }
                    responseList = forecastList[0].response
                }

                override fun onError(t: Throwable?) {
                    Toast.makeText(context, resources.getString(R.string.error), Toast.LENGTH_LONG)
                        .show()
                    if (t != null) {
                        Log.d(TAG, "onError: ${t.message}")
                    } else {
                        Log.d(TAG, "onError: t == null")
                    }
                }

                override fun onComplete() {
                    val adapter =
                        WeatherForecastAdapter(requireActivity().applicationContext, responseList)
                    recycler.adapter = adapter

                    loading.visibility = View.GONE
                    progressBar.visibility = View.GONE
                }
            })
    }

    private fun initViews() {
        recycler = requireActivity().findViewById(R.id.recycler_weather_forecast)
        loading = requireActivity().findViewById(R.id.loading_weather_forecast)
        progressBar = requireActivity().findViewById(R.id.progress_bar_weather_forecast)
    }
}