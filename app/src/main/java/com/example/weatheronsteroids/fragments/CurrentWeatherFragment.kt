package com.example.weatheronsteroids.fragments

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
import com.example.weatheronsteroids.R
import com.example.weatheronsteroids.model.OpenWeatherMapApi
import com.example.weatheronsteroids.model.Response
import com.squareup.picasso.Picasso
import dagger.Lazy
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subscribers.DisposableSubscriber
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class CurrentWeatherFragment : Fragment() {

    private val APP_ID = "511180"
    private val API_KEY = "3767cbc63512e48175b64b1b5664d14c"
    private val LANG = "ru"
    private val UNITS = "metric"

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

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .baseUrl("https://api.openweathermap.org")
        .build()

    private val api = retrofit.create(OpenWeatherMapApi::class.java)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_current_weather, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initViews()
        val flowable: Flowable<Response> = api.getResponse(APP_ID, API_KEY, LANG, UNITS)
        setupFlowable(flowable)
    }

    private fun setupFlowable(flowable: Flowable<Response>) {
        flowable.take(1)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : DisposableSubscriber<Response>() {

                override fun onNext(t: Response?) {
                    Log.d(TAG, "onNext")

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

                    loading.visibility = View.GONE
                    progressBar.visibility = View.GONE
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
                    Log.d(TAG, "onComplete")
                }
            })
    }

    private fun initViews() {
        icon = requireActivity().findViewById(R.id.icon)
        description = requireActivity().findViewById(R.id.description)
        temp = requireActivity().findViewById(R.id.temp)
        feelsLike = requireActivity().findViewById(R.id.feels_like)
        pressure = requireActivity().findViewById(R.id.pressure)
        humidity = requireActivity().findViewById(R.id.humidity)
        speed = requireActivity().findViewById(R.id.speed)
        loading = requireActivity().findViewById(R.id.loading)
        progressBar = requireActivity().findViewById(R.id.progress_bar)
    }
}