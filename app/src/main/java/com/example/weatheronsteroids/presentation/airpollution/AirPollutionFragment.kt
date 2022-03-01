package com.example.weatheronsteroids.presentation.airpollution

import android.os.Bundle
import android.widget.ProgressBar
import androidx.appcompat.widget.AppCompatTextView
import com.example.weatheronsteroids.R
import com.example.weatheronsteroids.app.App
import com.example.weatheronsteroids.entity.CurrentAirPollution
import com.example.weatheronsteroids.domain.utils.gone
import com.example.weatheronsteroids.domain.utils.string
import moxy.MvpAppCompatFragment
import javax.inject.Inject

class AirPollutionFragment : MvpAppCompatFragment(R.layout.fragment_air_pollution),
    com.example.weatheronsteroids.presentation.airpollution.AirPollutionView {

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
    lateinit var presenter: com.example.weatheronsteroids.presentation.airpollution.AirPollutionPresenter

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
        airRate.text = t.list?.get(0)?.main?.let { it ->
            String.format(
                "%s %s (%s)",
                airRate.text,
                it.aqi,
                t.list!![0].main?.aqi?.let { getRate(it) }
            )
        }

        co.text = String.format(
            "%s %s",
            context?.string(R.string.co_rate), t.list?.get(0)?.components?.co
        )

        no.text = String.format(
            "%s %s",
            context?.string(R.string.no_rate), t.list?.get(0)?.components?.no
        )

        no2.text = String.format(
            "%s %s",
            context?.string(R.string.no2_rate), t.list?.get(0)?.components?.no2
        )

        o3.text = String.format(
            "%s %s",
            context?.string(R.string.o3_rate), t.list?.get(0)?.components?.o3
        )

        so2.text = String.format(
            "%s %s",
            context?.string(R.string.so2_rate), t.list?.get(0)?.components?.so2
        )

        pm25.text = String.format(
            "%s %s",
            context?.string(R.string.pm25_rate), t.list?.get(0)?.components?.pm2_5
        )

        pm10.text = String.format(
            "%s %s",
            context?.string(R.string.pm10_rate), t.list?.get(0)?.components?.pm10
        )

        nh3.text = String.format(
            "%s %s",
            context?.string(R.string.nh3_rate), t.list?.get(0)?.components?.nh3
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