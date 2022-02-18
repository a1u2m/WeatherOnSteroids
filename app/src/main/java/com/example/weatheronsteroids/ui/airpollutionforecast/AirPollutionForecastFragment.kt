package com.example.weatheronsteroids.ui.airpollutionforecast

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weatheronsteroids.R
import com.example.weatheronsteroids.di.App
import com.example.weatheronsteroids.model.AirQualityAndComponents
import moxy.MvpAppCompatFragment
import javax.inject.Inject

class AirPollutionForecastFragment : MvpAppCompatFragment(R.layout.fragment_air_pollution_forecast), AirPollutionForecastView {

    private val TAG = "AirPollutionForecastFragment"

    lateinit var dateRecycler: RecyclerView
    lateinit var recycler: RecyclerView
    lateinit var loading: AppCompatTextView
    lateinit var progressBar: ProgressBar

    private val forecastList = mutableListOf<AirQualityAndComponents>()
    private var dateList = mutableListOf<String>()

    @Inject
    lateinit var presenter: AirPollutionForecastPresenter

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
        dateRecycler = requireActivity().findViewById(R.id.air_pollution_forecast_dates_recycler)
        recycler = requireActivity().findViewById(R.id.air_pollution_forecast_recycler)
        loading = requireActivity().findViewById(R.id.air_pollution_forecast_loading)
        progressBar = requireActivity().findViewById(R.id.air_pollution_forecast_progress_bar)
        presenter.attachView(this)
    }

    override fun fillViews(t: MutableList<AirQualityAndComponents>) {
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
            AirPollutionForecastAdapter(requireActivity().applicationContext, forecastList)
        val dateAdapter = AirDateAdapter(requireActivity().applicationContext, dateList)

        recycler.adapter = adapter
        dateRecycler.adapter = dateAdapter
    }

    override fun resetAdapter() {
        val adapter =
            AirPollutionForecastAdapter(requireActivity().applicationContext, forecastList)
        recycler.adapter = adapter
    }
}