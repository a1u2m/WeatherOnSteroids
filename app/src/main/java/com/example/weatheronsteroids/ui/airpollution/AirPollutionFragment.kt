package com.example.weatheronsteroids.ui.airpollution

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.widget.AppCompatTextView
import com.example.weatheronsteroids.R
import com.example.weatheronsteroids.di.App
import com.example.weatheronsteroids.model.CurrentAirPollution
import com.example.weatheronsteroids.utils.gone
import moxy.MvpAppCompatFragment
import javax.inject.Inject

class AirPollutionFragment : MvpAppCompatFragment(R.layout.fragment_air_pollution),
    AirPollutionView {

    private val TAG = "AirPollutionFragment"

    lateinit var airRate: AppCompatTextView
    lateinit var co: AppCompatTextView
    lateinit var no: AppCompatTextView
    lateinit var no2: AppCompatTextView
    lateinit var o3: AppCompatTextView
    lateinit var so2: AppCompatTextView
    lateinit var pm25: AppCompatTextView
    lateinit var pm10: AppCompatTextView
    lateinit var nh3: AppCompatTextView
    lateinit var progressBar: ProgressBar
    lateinit var loading: AppCompatTextView

    @Inject
    lateinit var presenter: AirPollutionPresenter

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
        airRate = requireActivity().findViewById(R.id.air_pollution_current_air_rate)
        co = requireActivity().findViewById(R.id.air_pollution_current_co)
        no = requireActivity().findViewById(R.id.air_pollution_current_no)
        no2 = requireActivity().findViewById(R.id.air_pollution_current_no2)
        o3 = requireActivity().findViewById(R.id.air_pollution_current_o3)
        so2 = requireActivity().findViewById(R.id.air_pollution_current_so2)
        pm25 = requireActivity().findViewById(R.id.air_pollution_current_pm2_5)
        pm10 = requireActivity().findViewById(R.id.air_pollution_current_pm10)
        nh3 = requireActivity().findViewById(R.id.air_pollution_current_nh3)
        progressBar = requireActivity().findViewById(R.id.air_pollution_current_progress_bar)
        loading = requireActivity().findViewById(R.id.air_pollution_current_loading)
        presenter.attachView(this)
    }

    override fun fillViews(t: CurrentAirPollution) {
        airRate.text = String.format(
            "%s %s (%s)",
            airRate.text,
            t.airQualityAndComponents[0].airPollutionMain.aqi,
            getRate(t.airQualityAndComponents[0].airPollutionMain.aqi)
        )

        co.text = String.format(
            "%s %s",
            co.text, t.airQualityAndComponents[0].airPollutionComponents.co
        )

        no.text = String.format(
            "%s %s",
            no.text, t.airQualityAndComponents[0].airPollutionComponents.no
        )

        no2.text = String.format(
            "%s %s",
            no2.text, t.airQualityAndComponents[0].airPollutionComponents.no2
        )

        o3.text = String.format(
            "%s %s",
            o3.text, t.airQualityAndComponents[0].airPollutionComponents.o3
        )

        so2.text = String.format(
            "%s %s",
            so2.text, t.airQualityAndComponents[0].airPollutionComponents.so2
        )

        pm25.text = String.format(
            "%s %s",
            pm25.text, t.airQualityAndComponents[0].airPollutionComponents.pm2_5
        )

        pm10.text = String.format(
            "%s %s",
            pm10.text, t.airQualityAndComponents[0].airPollutionComponents.pm10
        )

        nh3.text = String.format(
            "%s %s",
            nh3.text, t.airQualityAndComponents[0].airPollutionComponents.nh3
        )

    }

    private fun getRate(rate: String): String {
        return when (rate) {
            "1" -> "Отлично"
            "2" -> "Хорошо"
            "3" -> "Нормально"
            "4" -> "Плохо"
            "5" -> "Ужасно"
            else -> "Не удалось получить рейтинг"
        }
    }

    override fun hideProgressBar() {
        loading.gone()
        progressBar.gone()
    }
}