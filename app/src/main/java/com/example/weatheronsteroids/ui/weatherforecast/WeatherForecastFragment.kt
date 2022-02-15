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
import com.example.weatheronsteroids.model.Response
import moxy.MvpAppCompatFragment
import androidx.recyclerview.widget.LinearLayoutManager

class WeatherForecastFragment : MvpAppCompatFragment(), WeatherForecastView {

    private val TAG = "WeatherForecastFragment"

    lateinit var dateRecycler: RecyclerView
    lateinit var recycler: RecyclerView
    lateinit var loading: AppCompatTextView
    lateinit var progressBar: ProgressBar

    private val forecastList = mutableListOf<Response>()
    private var dateList = mutableListOf<String>()

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
        dateRecycler = requireActivity().findViewById(R.id.weather_forecast_dates_recycler)
        recycler = requireActivity().findViewById(R.id.weather_forecast_recycler)
        loading = requireActivity().findViewById(R.id.weather_forecast_loading)
        progressBar = requireActivity().findViewById(R.id.weather_forecast_progress_bar)
        presenter.attachView(this)
    }

    override fun fillViews(t: MutableList<Response>) {
        forecastList.clear()
        for (i in t.indices) {
            forecastList.add(t[i])
        }
    }



    override fun fillDates(t: MutableSet<String>) {
        dateList.addAll(t)
    }

    override fun hideProgressBar() {
        loading.visibility = View.GONE
        progressBar.visibility = View.GONE
    }

    override fun setAdapter() {
        val horizontalLayout = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        dateRecycler.layoutManager = horizontalLayout

        val adapter =
            WeatherForecastAdapter(requireActivity().applicationContext, forecastList)
        val dateAdapter = WeatherDateAdapter(requireActivity().applicationContext, dateList)

        recycler.adapter = adapter
        dateRecycler.adapter = dateAdapter
    }

    override fun resetAdapter() {
        val adapter =
            WeatherForecastAdapter(requireActivity().applicationContext, forecastList)

        recycler.adapter = adapter
    }
}