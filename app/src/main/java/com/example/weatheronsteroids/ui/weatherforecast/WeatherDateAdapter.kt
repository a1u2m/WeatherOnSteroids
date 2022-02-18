package com.example.weatheronsteroids.ui.weatherforecast

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.example.weatheronsteroids.R
import com.example.weatheronsteroids.di.App
import com.example.weatheronsteroids.utils.string
import javax.inject.Inject

class WeatherDateAdapter(private val context: Context, val list: List<String>) :
    RecyclerView.Adapter<WeatherDateAdapter.ViewHolder>() {

    private val TAG = "DateAdapter"

    @Inject
    lateinit var presenter: WeatherForecastPresenter

    private val inflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = inflater.inflate(R.layout.dates_item, parent, false)
        (context.applicationContext as App).appComponent.inject(this)

        view.setOnClickListener { p0 ->
            val itemPosition = (parent as RecyclerView).getChildLayoutPosition(p0!!)
            val item = list.get(itemPosition)
            presenter.fillViews(item)
        }
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val date = list[position]

        holder.timeAndDate.text = String.format(
            "%s %s",
            context.string(R.string.date), date
        )
    }

    override fun getItemCount(): Int = list.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var timeAndDate: AppCompatTextView =
            itemView.findViewById(R.id.for_recycler_date)
    }
}