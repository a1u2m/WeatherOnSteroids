package com.example.weatheronsteroids.ui.weatherforecast

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.example.weatheronsteroids.R
import com.example.weatheronsteroids.di.App
import com.example.weatheronsteroids.model.Forecast
import com.example.weatheronsteroids.model.Response
import moxy.MvpAppCompatFragment

class WeatherForecastFragment : MvpAppCompatFragment(), WeatherForecastView {

    private val TAG = "WeatherForecastFragment"

    lateinit var recycler: RecyclerView
    lateinit var loading: AppCompatTextView
    lateinit var progressBar: ProgressBar

    private val forecastList = mutableListOf<Forecast>()
    private var responseList = listOf<Response>()

    lateinit var presenter: WeatherForecastPresenter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_weather_forecast, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        presenter = (activity?.application as App).appComponent.getWeatherForecastPresenter()
        init()
        presenter.setupFlowable()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView(this)
    }

    private fun init() {
        recycler = requireActivity().findViewById(R.id.weather_forecast_recycler)
        loading = requireActivity().findViewById(R.id.weather_forecast_loading)
        progressBar = requireActivity().findViewById(R.id.weather_forecast_progress_bar)
        presenter.attachView(this)
    }

    override fun fillViews(t: Forecast) {
        forecastList.add(t)
        responseList = forecastList[0].response
    }

    override fun hideProgressBar() {
        loading.visibility = View.GONE
        progressBar.visibility = View.GONE
    }

    override fun setAdapter() {
        val adapter =
            WeatherForecastAdapter(requireActivity().applicationContext, responseList)
        recycler.adapter = adapter
    }
}