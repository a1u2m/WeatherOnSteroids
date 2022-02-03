package com.example.weatheronsteroids.fragments

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
import com.example.weatheronsteroids.WeatherForecastAdapter
import com.example.weatheronsteroids.R
import com.example.weatheronsteroids.model.Forecast
import com.example.weatheronsteroids.model.OpenWeatherMapApi
import com.example.weatheronsteroids.model.Response
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subscribers.DisposableSubscriber
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class WeatherForecastFragment : Fragment() {

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

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .baseUrl("https://api.openweathermap.org")
        .build()

    private val api = retrofit.create(OpenWeatherMapApi::class.java)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_weather_forecast, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initViews()

        val responseFlowable: Flowable<Forecast> = api.getWeatherForecast(ID, API_KEY, LANG, UNITS)
        setupFlowable(responseFlowable)
    }

    private fun setupFlowable(flowable: Flowable<Forecast>) {
        flowable.take(1)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : DisposableSubscriber<Forecast>() {

                override fun onNext(t: Forecast?) {
                    Log.d(TAG, "onNext forecast")
                    Log.d(TAG, "forecast ${t?.response?.get(0)?.dt_txt}")

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
                    Log.d(TAG, "onComplete")
                    val adapter = WeatherForecastAdapter(requireActivity().applicationContext, responseList)
                    recycler.adapter = adapter

                    loading.visibility = View.GONE
                    progressBar.visibility = View.GONE
                }
            })
    }

    private fun initViews() {
        recycler = requireActivity().findViewById(R.id.recycler_forecast)
        loading = requireActivity().findViewById(R.id.loading_forecast)
        progressBar = requireActivity().findViewById(R.id.progress_bar_forecast)
    }
}