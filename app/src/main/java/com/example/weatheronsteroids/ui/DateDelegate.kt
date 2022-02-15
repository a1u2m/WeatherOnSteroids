package com.example.weatheronsteroids.ui

import android.view.View
import android.view.View.inflate
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.example.weatheronsteroids.R

//internal class DateDelegate: AdapterDelegate<DisplayableItem> {
//
//    override fun isForViewType(items: List<DisplayableItem>, position: Int): Boolean {
//        return items[position] is WeatherDate
//    }
//
//    override fun onCreateViewHolder(
//        parent: ViewGroup,
//    ): DateViewHolder {
//        return  DateViewHolder(inflate(R.layout.dates_item, false))
//    }
//
//    override fun onBindViewHolder(
//        holder: RecyclerView.ViewHolder,
//        items: List<DisplayableItem>,
//        position: Int
//    ) {
//        val responseList = items[position]
//
//        (holder as DateViewHolder).bind(items[position] as WeatherDate)
//
////        holder.timeAndDate.text = "${context.resources.getString(R.string.time_and_date)} ${
////            transformDateToRussian(responseList)
////        }"
//    }
//
//    inner class DateViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        var timeAndDate: AppCompatTextView =
//            itemView.findViewById(R.id.weather_forecast_time_and_date)
//
//        fun bind(weatherDate: WeatherDate) {
//
//        }
//    }
//
//}