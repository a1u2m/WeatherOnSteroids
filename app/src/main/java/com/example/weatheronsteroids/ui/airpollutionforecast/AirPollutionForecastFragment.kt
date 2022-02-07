package com.example.weatheronsteroids.ui.airpollutionforecast

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
import com.example.weatheronsteroids.model.*

class AirPollutionForecastFragment : Fragment(), AirPollutionForecastView {

    private val TAG = "AirPollutionForecastFragment"

    lateinit var recycler: RecyclerView
    lateinit var loading: AppCompatTextView
    lateinit var progressBar: ProgressBar

    private val forecastList = mutableListOf<CurrentAirPollution>()
    private var responseList = listOf<AirQualityAndComponents>()

    private lateinit var presenter: AirPollutionForecastPresenter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_air_pollution_forecast, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        presenter = (activity?.application as App).appComponent.getAirPollutionForecastPresenter()
        init()
        presenter.setupFlowable()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }

    private fun init() {
        recycler = requireActivity().findViewById(R.id.recycler_air_pollution_forecast)
        loading = requireActivity().findViewById(R.id.loading_air_pollution_forecast)
        progressBar = requireActivity().findViewById(R.id.progress_bar_air_pollution_forecast)
        presenter.attachView(this)
    }

    override fun fillViews(t: CurrentAirPollution) {
        forecastList.add(t)
        responseList = forecastList[0].airQualityAndComponents
    }

    override fun showToast() {
        Toast.makeText(context, resources.getString(R.string.error), Toast.LENGTH_LONG)
            .show()
    }

    override fun hideProgressBar() {
        loading.visibility = View.GONE
        progressBar.visibility = View.GONE
    }

    override fun setAdapter() {
        val adapter =
            AirPollutionForecastAdapter(requireActivity().applicationContext, responseList)
        recycler.adapter = adapter
    }
}