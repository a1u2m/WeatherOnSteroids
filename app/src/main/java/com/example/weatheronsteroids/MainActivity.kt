package com.example.weatheronsteroids

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.jakewharton.rxbinding4.view.clicks

class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"

    private var fragment = Fragment()

    private lateinit var bottomPanel: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()
    }

    private fun initViews() {
        bottomPanel = findViewById(R.id.bottom_panel)

        fragment = CurrentWeatherFragment()
        showFragment(fragment, R.id.fragment_container)

        bottomPanel.setOnItemSelectedListener { menu -> setupBottomNavigationMenu(menu) }
    }

    private fun setupBottomNavigationMenu(menu: MenuItem) = when (menu.itemId) {
        R.id.current_weather -> {
            fragment = CurrentWeatherFragment()
            showFragment(fragment, R.id.fragment_container)
            true
        }

        R.id.five_days_forecast -> {
            fragment = WeatherForecastFragment()
            showFragment(fragment, R.id.fragment_container)
            true
        }

        R.id.weather_map -> {
            fragment = WeatherMapFragment()
            showFragment(fragment, R.id.fragment_container)
            true
        }

        R.id.air_pollution -> {
            fragment = AirPollutionFragment()
            showFragment(fragment, R.id.fragment_container)
            true
        }

        R.id.settings -> {
            fragment = SettingsFragment()
            showFragment(fragment, R.id.fragment_container)
            true
        }
        else -> false
    }


    private fun showFragment(fragment: Fragment, id: Int) {
        val frame = supportFragmentManager.beginTransaction()
        frame.replace(id, fragment)
        frame.commit()
        Log.d(TAG, "showfragment $fragment")
    }
}