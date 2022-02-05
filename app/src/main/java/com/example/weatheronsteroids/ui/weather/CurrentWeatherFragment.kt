package com.example.weatheronsteroids.ui.weather

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import com.example.weatheronsteroids.ui.main.MainActivity
import com.example.weatheronsteroids.R
import com.example.weatheronsteroids.network.OpenWeatherMapApi
import com.example.weatheronsteroids.model.Response
import com.example.weatheronsteroids.utils.secrettextview.SecretTextView
import com.squareup.picasso.Picasso
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subscribers.DisposableSubscriber
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit

class CurrentWeatherFragment : Fragment() {

    private val TAG = "CurrentWeatherFragment"

    private val ID = "511180"
    private val API_KEY = "3767cbc63512e48175b64b1b5664d14c"
    private val LANG = "ru"
    private val UNITS = "metric"

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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_current_weather, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initViews()

        if ((activity as MainActivity).isCanGreet) {
            val spName = (activity as MainActivity).sp.getUserName()
            Log.d(TAG, "$spName")
            greetings.text = "${getGreeting()} ${if (spName != "user_name_key") spName else resources.getString(R.string.user)}"
            greetings.show()
            hideGreetings()
            (activity as MainActivity).isCanGreet = false
        }

        val responseFlowable: Flowable<Response> = (activity as MainActivity).retrofitHelper.getApi().getCurrentWeather(ID, API_KEY, LANG, UNITS)
        setupFlowable(responseFlowable)
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

    private fun setupFlowable(flowable: Flowable<Response>) {
        flowable.take(1)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : DisposableSubscriber<Response>() {

                override fun onNext(t: Response?) {
                    description.text =
                        "${description.text} ${t?.weather?.get(0)?.description?.capitalize()}"
                    temp.text = "${temp.text} ${t?.main?.temp}°C"
                    feelsLike.text = "${feelsLike.text} ${t?.main?.feelsLike}°C"
                    pressure.text = "${pressure.text} ${t?.main?.pressure} мм рт. ст."
                    humidity.text = "${humidity.text} ${t?.main?.humidity}%"
                    speed.text = "${speed.text} ${t?.wind?.speed} м/с"

                    val pictureLink =
                        "https://openweathermap.org/img/wn/${t?.weather?.get(0)?.icon}@2x.png"

                    Picasso.get()
                        .load(pictureLink)
                        .error(R.drawable.ic_weather_placeholder)
                        .into(icon)
                }

                override fun onError(t: Throwable?) {
                    Toast.makeText(context, resources.getString(R.string.error), Toast.LENGTH_LONG)
                        .show()
                    if (t != null) {
                        Log.d(TAG, "onError: ${t.message}")
                    } else {
                        Log.d(TAG, "onError: t == null")
                    }
                }

                override fun onComplete() {
                    loading.visibility = View.GONE
                    progressBar.visibility = View.GONE
                }
            })
    }

    private fun initViews() {
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
}