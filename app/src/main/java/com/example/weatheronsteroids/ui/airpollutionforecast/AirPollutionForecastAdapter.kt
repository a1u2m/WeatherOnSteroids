package com.example.weatheronsteroids.ui.airpollutionforecast

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.example.weatheronsteroids.R
import com.example.weatheronsteroids.model.AirQualityAndComponents
import com.example.weatheronsteroids.model.Response
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

        holder.time.text =
            "${context.resources.getString(R.string.time)} ${epochToDate(responseList.dt)}"
        holder.airRate.text =
            "${context.resources.getString(R.string.air_rate)} ${responseList.airPollutionMain.aqi}"
        holder.co.text =
            "${context.resources.getString(R.string.co_rate)} ${responseList.airPollutionComponents.co}"
        holder.no.text =
            "${context.resources.getString(R.string.no_rate)} ${responseList.airPollutionComponents.no}"
        holder.no2.text =
            "${context.resources.getString(R.string.no2_rate)} ${responseList.airPollutionComponents.no2}"
        holder.o3.text =
            "${context.resources.getString(R.string.o3_rate)} ${responseList.airPollutionComponents.o3}"
        holder.so2.text =
            "${context.resources.getString(R.string.so2_rate)} ${responseList.airPollutionComponents.so2}"
        holder.pm25.text =
            "${context.resources.getString(R.string.pm25_rate)} ${responseList.airPollutionComponents.pm2_5}"
        holder.pm10.text =
            "${context.resources.getString(R.string.pm10_rate)} ${responseList.airPollutionComponents.pm10}"
        holder.nh3.text =
            "${context.resources.getString(R.string.nh3_rate)} ${responseList.airPollutionComponents.nh3}"
    }

    override fun getItemCount(): Int {
        return list.size
    }

    private fun epochToDate(string: String): String {
        val sdf = SimpleDateFormat("HH:mm:ss")
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