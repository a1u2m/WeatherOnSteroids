package com.example.weatheronsteroids.presentation.weatherforecast

import android.os.Bundle
import android.widget.ProgressBar
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.example.weatheronsteroids.R
import com.example.weatheronsteroids.app.App
import com.example.weatheronsteroids.entity.Response
import moxy.MvpAppCompatFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatheronsteroids.domain.utils.gone
import javax.inject.Inject

class WeatherForecastFragment : MvpAppCompatFragment(R.layout.fragment_weather_forecast), WeatherForecastView {

    private val TAG = "WeatherForecastFragment"

    lateinit var dateRecycler: RecyclerView
    lateinit var recycler: RecyclerView
    lateinit var loading: AppCompatTextView
    lateinit var progressBar: ProgressBar

    private val forecastList = mutableListOf<Response>()
    private var dateList = mutableListOf<String>()

    @Inject
    lateinit var presenter: WeatherForecastPresenter

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (activity?.application as App).appComponent.inject(this)
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
        loading.gone()
        progressBar.gone()
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