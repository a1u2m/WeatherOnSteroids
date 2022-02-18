package com.example.weatheronsteroids.ui.weatherforecast

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.example.weatheronsteroids.R
import com.example.weatheronsteroids.model.Response
import com.example.weatheronsteroids.utils.string
import com.squareup.picasso.Picasso

class WeatherForecastAdapter(private val context: Context, private val list: List<Response>) :
    RecyclerView.Adapter<WeatherForecastAdapter.ViewHolder>() {

    private val inflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = inflater.inflate(R.layout.weather_forecast_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val responseList = list[position]

        val pictureLink =
            "https://openweathermap.org/img/wn/${responseList.weather[0].icon}@2x.png"

        holder.time.text = "${context.string(R.string.time)} ${
            transformDateToRussian(responseList)
        }"
        holder.description.text =
            "${context.resources.getString(R.string.description)} ${responseList.weather[0].description.capitalize()}"
        holder.temp.text =
            "${context.resources.getString(R.string.temp)} ${responseList.main.temp}°C"
        holder.feelsLike.text =
            "${context.resources.getString(R.string.feels_like)} ${responseList.main.feelsLike}°C"
        holder.pressure.text =
            "${context.resources.getString(R.string.pressure)} ${responseList.main.pressure} мм рт. ст."
        holder.humidity.text =
            "${context.resources.getString(R.string.humidity)} ${responseList.main.humidity}%"
        holder.speed.text =
            "${context.resources.getString(R.string.speed)} ${responseList.wind.speed} м/с"

        Picasso.get()
            .load(pictureLink)
            .error(R.drawable.ic_weather_placeholder)
            .into(holder.icon)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    private fun transformDateToRussian(responseList: Response): StringBuilder {
        val sb = StringBuilder()
        val timeAndDate = responseList.dt_txt
        val time = timeAndDate.substring(11)
        sb.append(time)
        return sb
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var icon: AppCompatImageView = itemView.findViewById(R.id.weather_forecast_icon)
        var time: AppCompatTextView =
            itemView.findViewById(R.id.weather_forecast_time)
        var description: AppCompatTextView =
            itemView.findViewById(R.id.weather_forecast_description)
        var temp: AppCompatTextView = itemView.findViewById(R.id.weather_forecast_temp)
        var feelsLike: AppCompatTextView = itemView.findViewById(R.id.weather_forecast_feels_like)
        var pressure: AppCompatTextView = itemView.findViewById(R.id.weather_forecast_pressure)
        var humidity: AppCompatTextView = itemView.findViewById(R.id.weather_forecast_humidity)
        var speed: AppCompatTextView = itemView.findViewById(R.id.weather_forecast_speed)
    }
}