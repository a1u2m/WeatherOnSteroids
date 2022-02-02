package com.example.weatheronsteroids

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.example.weatheronsteroids.model.Response
import com.squareup.picasso.Picasso
import kotlin.coroutines.coroutineContext

class ForecastAdapter(private val context: Context, private val list: List<Response>) : RecyclerView.Adapter<ForecastAdapter.ViewHolder>() {

    private val inflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = inflater.inflate(R.layout.recycler_forecast_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val responseList = list[position]

        val pictureLink =
            "https://openweathermap.org/img/wn/${responseList.weather[0].icon}@2x.png"

        holder.timeAndDate.text = "${context.resources.getString(R.string.time_and_date)} ${transformDateToRussian(responseList)}"
        holder.description.text = "${context.resources.getString(R.string.description)} ${responseList.weather[0].description.capitalize()}"
        holder.temp.text = "${context.resources.getString(R.string.temp)} ${responseList.main.temp}°C"
        holder.feelsLike.text = "${context.resources.getString(R.string.feels_like)} ${responseList.main.feelsLike}°C"
        holder.pressure.text = "${context.resources.getString(R.string.pressure)} ${responseList.main.pressure} мм рт. ст."
        holder.humidity.text = "${context.resources.getString(R.string.humidity)} ${responseList.main.humidity}%"
        holder.speed.text = "${context.resources.getString(R.string.speed)} ${responseList.wind.speed} м/с"

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
        val date = timeAndDate.substring(8, 10)
        val month = timeAndDate.substring(5, 7)
        val year = timeAndDate.substring(0, 4)
        val time = timeAndDate.substring(11)
        sb.append(date)
        sb.append("-")
        sb.append(month)
        sb.append("-")
        sb.append(year)
        sb.append(" ")
        sb.append(time)
        return sb
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        var icon: AppCompatImageView = itemView.findViewById(R.id.icon_forecast)
        var timeAndDate: AppCompatTextView = itemView.findViewById(R.id.time_and_date_forecast)
        var description: AppCompatTextView = itemView.findViewById(R.id.description_forecast)
        var temp: AppCompatTextView = itemView.findViewById(R.id.temp_forecast)
        var feelsLike: AppCompatTextView = itemView.findViewById(R.id.feels_like_forecast)
        var pressure: AppCompatTextView = itemView.findViewById(R.id.pressure_forecast)
        var humidity: AppCompatTextView = itemView.findViewById(R.id.humidity_forecast)
        var speed: AppCompatTextView = itemView.findViewById(R.id.speed_forecast)
    }
}