package com.example.weatheronsteroids.ui.weather

import android.os.Bundle
import android.util.Log
import android.widget.ProgressBar
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import com.example.weatheronsteroids.R
import com.example.weatheronsteroids.app.App
import com.example.weatheronsteroids.model.Response
import com.example.weatheronsteroids.utils.gone
import com.example.weatheronsteroids.utils.load
import com.example.weatheronsteroids.utils.secrettextview.SecretTextView
import com.example.weatheronsteroids.utils.string
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subscribers.DisposableSubscriber
import moxy.MvpAppCompatFragment
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class CurrentWeatherFragment : MvpAppCompatFragment(R.layout.fragment_current_weather),
    CurrentWeatherView {

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

    @Inject
    lateinit var presenter: CurrentWeatherPresenter

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (activity?.application as App).appComponent.inject(this)
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
        icon = requireActivity().findViewById(R.id.weather_current_icon)
        description = requireActivity().findViewById(R.id.weather_current_description)
        temp = requireActivity().findViewById(R.id.weather_current_temp)
        feelsLike = requireActivity().findViewById(R.id.weather_current_feels_like)
        pressure = requireActivity().findViewById(R.id.weather_current_pressure)
        humidity = requireActivity().findViewById(R.id.weather_current_humidity)
        speed = requireActivity().findViewById(R.id.weather_current_speed)
        loading = requireActivity().findViewById(R.id.weather_current_loading)
        progressBar = requireActivity().findViewById(R.id.weather_current_progress_bar)
        greetings = requireActivity().findViewById(R.id.weather_current_greetings)
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
        description.text = String.format(
            "%s %s",
            description.text, t.weather?.get(0)?.description?.replaceFirstChar {
                if (it.isLowerCase()) it.titlecase(
                    Locale.getDefault()
                ) else it.toString()
            }
        )

        temp.text = String.format(
            "%s %s°C",
            context?.string(R.string.temp), t.main?.temp
        )

        feelsLike.text = String.format(
            "%s %s°C",
            context?.string(R.string.feels_like), t.main?.feels_like
        )

        pressure.text = String.format(
            "%s %s мм рт. ст.",
            context?.string(R.string.pressure), t.main?.pressure
        )

        humidity.text = String.format(
            "%s %s%%",
            context?.string(R.string.humidity), t.main?.humidity
        )

        speed.text = String.format(
            "%s %s м/с",
            context?.string(R.string.speed), t.wind?.speed
        )

        val pictureLink =
            "https://openweathermap.org/img/wn/${t.weather?.get(0)?.icon}@2x.png"

        load(pictureLink, icon)
    }

    override fun hideProgressBar() {
        loading.gone()
        progressBar.gone()
    }

    override fun greetUser(name: String) {
        val greeting =
            "${getGreeting()} ${if (name != "user_name_key") name else context?.string(R.string.user)}"
        greetings.text = greeting
        greetings.show()
        hideGreetings()
    }
}