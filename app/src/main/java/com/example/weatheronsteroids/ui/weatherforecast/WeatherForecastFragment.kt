package com.example.weatheronsteroids.ui.weatherforecast

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.example.weatheronsteroids.R
import com.example.weatheronsteroids.di.App
import com.example.weatheronsteroids.model.Forecast
import com.example.weatheronsteroids.model.Response

class WeatherForecastFragment : Fragment(), WeatherForecastView {

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
        presenter.detachView()
    }

    private fun init() {
        recycler = requireActivity().findViewById(R.id.recycler_weather_forecast)
        loading = requireActivity().findViewById(R.id.loading_weather_forecast)
        progressBar = requireActivity().findViewById(R.id.progress_bar_weather_forecast)
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