package com.example.weatheronsteroids.ui.main

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.fragment.app.Fragment
import com.example.weatheronsteroids.R
import com.example.weatheronsteroids.data.SharedPreferencesHelper
import com.example.weatheronsteroids.network.RetrofitHelper
import com.example.weatheronsteroids.ui.airpollution.AirPollutionFragment
import com.example.weatheronsteroids.ui.airpollutionforecast.AirPollutionForecastFragment
import com.example.weatheronsteroids.ui.settings.SettingsFragment
import com.example.weatheronsteroids.ui.weather.CurrentWeatherFragment
import com.example.weatheronsteroids.ui.weatherforecast.WeatherForecastFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.observers.DisposableObserver
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"

    private var fragment = Fragment()
    private lateinit var bottomPanel: BottomNavigationView
    lateinit var sp: SharedPreferencesHelper
    lateinit var retrofitHelper: RetrofitHelper

    var isCanGreet = true
    var timeCount = 0

    private val timeCountObservable = Observable.interval(1, TimeUnit.SECONDS)
    lateinit var timeCountDisposable: DisposableObserver<Long>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
        countLaunch()
    }

    override fun onResume() {
        super.onResume()
        createNewDisposableAndSubscribe()
    }

    override fun onPause() {
        super.onPause()
        sp.countTime()
    }

    private fun countLaunch() {
        sp.putLaunch()
    }

    private fun init() {
        bottomPanel = findViewById(R.id.bottom_panel)
        fragment = CurrentWeatherFragment()
        showFragment(fragment, R.id.fragment_container)
        bottomPanel.setOnItemSelectedListener { menu -> setupBottomNavigationMenu(menu) }
        sp = SharedPreferencesHelper(this)
        retrofitHelper = RetrofitHelper()
    }

    private fun setupBottomNavigationMenu(menu: MenuItem) = when (menu.itemId) {
        R.id.current_weather -> {
            if (fragment !is CurrentWeatherFragment) {
                fragment = CurrentWeatherFragment()
                showFragment(fragment, R.id.fragment_container)
                true
            } else {
                false
            }
        }

        R.id.five_days_forecast -> {
            if (fragment !is WeatherForecastFragment) {
                fragment = WeatherForecastFragment()
                showFragment(fragment, R.id.fragment_container)
                true
            } else {
                false
            }
        }

        R.id.air_pollution_forecast -> {
            if (fragment !is AirPollutionForecastFragment) {
                fragment = AirPollutionForecastFragment()
                showFragment(fragment, R.id.fragment_container)
                true
            } else {
                false
            }
        }

        R.id.air_pollution -> {
            if (fragment !is AirPollutionFragment) {
                fragment = AirPollutionFragment()
                showFragment(fragment, R.id.fragment_container)
                true
            } else {
                false
            }
        }

        R.id.settings -> {
            if (fragment !is SettingsFragment) {
                fragment = SettingsFragment()
                showFragment(fragment, R.id.fragment_container)
                true
            } else {
                false
            }
        }
        else -> false
    }


    private fun showFragment(fragment: Fragment, id: Int) {
        val frame = supportFragmentManager.beginTransaction()
        frame.replace(id, fragment)
        frame.commit()
    }

    private fun createNewDisposableAndSubscribe() {
        timeCountDisposable = object : DisposableObserver<Long>() {

            override fun onNext(t: Long?) {
                timeCount++
            }

            override fun onError(t: Throwable?) {
                Log.d(TAG, "timeCount onError: ${t?.message}")
            }

            override fun onComplete() {
                //do nothing
            }
        }
        timeCountObservable.subscribe(timeCountDisposable)
    }
}


