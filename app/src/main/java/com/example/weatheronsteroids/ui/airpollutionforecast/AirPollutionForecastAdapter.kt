package com.example.weatheronsteroids.ui.airpollutionforecast

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.example.weatheronsteroids.R
import com.example.weatheronsteroids.model.AirQualityAndComponents
import com.example.weatheronsteroids.utils.string
import java.text.SimpleDateFormat
import java.util.*

class AirPollutionForecastAdapter(
    private val context: Context,
    private val list: List<AirQualityAndComponents>
) : RecyclerView.Adapter<AirPollutionForecastAdapter.ViewHolder>() {

    private val inflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = inflater.inflate(R.layout.air_forecast_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val responseList = list[position]

        with(holder) {
            time.text = String.format(
                "%s %s",
                context.string(R.string.time), responseList.dt?.let { epochToDate(it) }
            )

            airRate.text = String.format(
                "%s %s",
                context.string(R.string.air_rate),
                responseList.main?.aqi
            )

            co.text = String.format(
                "%s %s",
                context.string(R.string.co_rate), responseList.components?.co
            )

            no.text = String.format(
                "%s %s",
                context.string(R.string.no_rate), responseList.components?.no
            )

            no2.text = String.format(
                "%s %s",
                context.string(R.string.no2_rate), responseList.components?.no2
            )

            o3.text = String.format(
                "%s %s",
                context.string(R.string.o3_rate), responseList.components?.o3
            )

            so2.text = String.format(
                "%s %s",
                context.string(R.string.so2_rate), responseList.components?.so2
            )

            pm25.text = String.format(
                "%s %s",
                context.string(R.string.pm25_rate), responseList.components?.pm2_5
            )

            pm10.text = String.format(
                "%s %s",
                context.string(R.string.pm10_rate), responseList.components?.pm10
            )

            nh3.text = String.format(
                "%s %s",
                context.string(R.string.nh3_rate), responseList.components?.nh3
            )
        }
    }

    override fun getItemCount(): Int = list.size


    private fun epochToDate(string: String): String {
        val sdf = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
        val time = Date(string.toLong() * 1000)
        return sdf.format(time)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val time: AppCompatTextView =
            itemView.findViewById(R.id.air_pollution_forecast_time)
        val airRate: AppCompatTextView = itemView.findViewById(R.id.air_pollution_forecast_air_rate)
        val co: AppCompatTextView = itemView.findViewById(R.id.air_pollution_forecast_co)
        val no: AppCompatTextView = itemView.findViewById(R.id.air_pollution_forecast_no)
        val no2: AppCompatTextView = itemView.findViewById(R.id.air_pollution_forecast_no2)
        val o3: AppCompatTextView = itemView.findViewById(R.id.air_pollution_forecast_o3)
        val so2: AppCompatTextView = itemView.findViewById(R.id.air_pollution_forecast_so2)
        val pm25: AppCompatTextView = itemView.findViewById(R.id.air_pollution_forecast_pm2_5)
        val pm10: AppCompatTextView = itemView.findViewById(R.id.air_pollution_forecast_pm10)
        val nh3: AppCompatTextView = itemView.findViewById(R.id.air_pollution_forecast_nh3)
    }
}