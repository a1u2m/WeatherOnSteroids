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

        holder.time.text = String.format(
            "%s %s",
            context.string(R.string.time), epochToDate(responseList.dt)
        )

        holder.airRate.text = String.format(
            "%s %s",
            holder.airRate.text,
            responseList.airPollutionMain.aqi
        )

        holder.co.text = String.format(
            "%s %s",
            holder.co.text, responseList.airPollutionComponents.co
        )

        holder.no.text = String.format(
            "%s %s",
            holder.no.text, responseList.airPollutionComponents.no
        )

        holder.no2.text = String.format(
            "%s %s",
            holder.no2.text, responseList.airPollutionComponents.no2
        )

        holder.o3.text = String.format(
            "%s %s",
            holder.o3.text, responseList.airPollutionComponents.o3
        )

        holder.so2.text = String.format(
            "%s %s",
            holder.so2.text, responseList.airPollutionComponents.so2
        )

        holder.pm25.text = String.format(
            "%s %s",
            holder.pm25.text, responseList.airPollutionComponents.pm2_5
        )

        holder.pm10.text = String.format(
            "%s %s",
            holder.pm10.text, responseList.airPollutionComponents.pm10
        )

        holder.nh3.text = String.format(
            "%s %s",
            holder.nh3.text, responseList.airPollutionComponents.nh3
        )
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