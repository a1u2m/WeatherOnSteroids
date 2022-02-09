package com.example.weatheronsteroids.ui.weather

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import com.example.weatheronsteroids.R
import com.example.weatheronsteroids.di.App
import com.example.weatheronsteroids.model.Response
import com.example.weatheronsteroids.utils.secrettextview.SecretTextView
import com.squareup.picasso.Picasso
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subscribers.DisposableSubscriber
import moxy.MvpAppCompatFragment
import java.util.*
import java.util.concurrent.TimeUnit

class CurrentWeatherFragment : MvpAppCompatFragment(), CurrentWeatherView {

    private val TAG = "CurrentWeatherFragment"

    lateinit var icon: AppCompatImageView
    lateinit var description: AppCompatTextView
    lateinit var temp: AppCompatTextView
    lateinit var feelsLike: AppCompatTextView
    lateinit var pressure: AppCompatTextView
    lateinit var humidity: AppCompatTextView
    lateinit var speed: AppCompatTextView
    lateinit var loading: AppCompatTextView
    lateinit var progressBar: ProgressBar
    lateinit var greetings: SecretTextView

    private lateinit var presenter: CurrentWeatherPresenter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_current_weather, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        presenter = (activity?.application as App).appComponent.getCurrentWeatherPresenter()
        init()
        presenter.isCanGreet()
        presenter.setupFlowable()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView(this)
    }

    private fun hideGreetings() {
        Flowable
            .interval(1, TimeUnit.SECONDS)
            .take(3)
            .takeLast(1)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : DisposableSubscriber<Long>() {

                override fun onNext(t: Long?) {
                    //do nothing
                }

                override fun onComplete() {
                    greetings.hide()
                }

                override fun onError(t: Throwable?) {
                    Log.d(TAG, "onError: ${t?.message}")
                }
            })
    }

    private fun init() {
        icon = requireActivity().findViewById(R.id.icon_current)
        description = requireActivity().findViewById(R.id.description_current)
        temp = requireActivity().findViewById(R.id.temp_current)
        feelsLike = requireActivity().findViewById(R.id.feels_like_current)
        pressure = requireActivity().findViewById(R.id.pressure_current)
        humidity = requireActivity().findViewById(R.id.humidity_current)
        speed = requireActivity().findViewById(R.id.speed_current)
        loading = requireActivity().findViewById(R.id.loading_current)
        progressBar = requireActivity().findViewById(R.id.progress_bar_current)
        greetings = requireActivity().findViewById(R.id.greetings_current)
        presenter.attachView(this)
    }

    private fun getGreeting(): String {
        return when (Calendar.getInstance().get(Calendar.HOUR_OF_DAY)) {
            0, 1, 2, 3, 4, 5 -> resources.getString(R.string.good_night)
            6, 7, 8, 9, 10, 11 -> resources.getString(R.string.good_morning)
            12, 13, 14, 15, 16, 17 -> resources.getString(R.string.good_day)
            18, 19, 20, 21, 22, 23 -> resources.getString(R.string.good_evening)
            else -> resources.getString(R.string.good_everything)
        }
    }

    override fun fillViews(t: Response) {
        description.text =
            "${description.text} ${t.weather.get(0).description.capitalize()}"
        temp.text = "${temp.text} ${t.main.temp}°C"
        feelsLike.text = "${feelsLike.text} ${t.main.feelsLike}°C"
        pressure.text = "${pressure.text} ${t.main.pressure} мм рт. ст."
        humidity.text = "${humidity.text} ${t.main.humidity}%"
        speed.text = "${speed.text} ${t.wind.speed} м/с"

        val pictureLink =
            "https://openweathermap.org/img/wn/${t.weather[0].icon}@2x.png"

        Picasso.get()
            .load(pictureLink)
            .error(R.drawable.ic_weather_placeholder)
            .into(icon)
    }

    override fun hideProgressBar() {
        loading.visibility = View.GONE
        progressBar.visibility = View.GONE
    }

    override fun greetUser(name: String) {
        greetings.text =
            "${getGreeting()} ${if (name != "user_name_key") name else resources.getString(R.string.user)}"
        greetings.show()
        hideGreetings()
    }
}