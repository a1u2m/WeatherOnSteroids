package com.example.weatheronsteroids.presentation.main

import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.Fragment
import com.example.weatheronsteroids.R
import com.example.weatheronsteroids.app.App
import com.example.weatheronsteroids.presentation.airpollution.AirPollutionFragment
import com.example.weatheronsteroids.presentation.airpollutionforecast.AirPollutionForecastFragment
import com.example.weatheronsteroids.presentation.settings.SettingsFragment
import com.example.weatheronsteroids.presentation.weather.CurrentWeatherFragment
import com.example.weatheronsteroids.presentation.weatherforecast.WeatherForecastFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import moxy.MvpAppCompatActivity
import javax.inject.Inject

class MainActivity : MvpAppCompatActivity(), MainView {

    private val TAG = "MainActivity"

    var fragment = Fragment()
    private lateinit var bottomPanel: BottomNavigationView

    @Inject
    lateinit var presenter: MainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        (application as App).appComponent.inject(this)
        init()
        countLaunch()
    }

    override fun onResume() {
        super.onResume()
        presenter.createNewDisposableAndSubscribe()
    }

    override fun onPause() {
        super.onPause()
        presenter.countTime()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.isCanGreetReset()
    }

    override fun countLaunch() {
        presenter.putLaunch()
    }

    override fun init() {
        bottomPanel = findViewById(R.id.bottom_panel)
        fragment = CurrentWeatherFragment()
        showFragment(fragment, R.id.fragment_container)
        bottomPanel.setOnItemSelectedListener { menu -> setupBottomNavigationMenu(menu) }
        presenter.attachView(this)
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

    override fun showFragment(fragment: Fragment, id: Int) {
        val frame = supportFragmentManager.beginTransaction()
        frame.replace(id, fragment)
        frame.commit()
    }
}


