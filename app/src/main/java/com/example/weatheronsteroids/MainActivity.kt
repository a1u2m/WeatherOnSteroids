package com.example.weatheronsteroids

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.fragment.app.Fragment
import com.example.weatheronsteroids.fragments.*
import com.google.android.material.bottomnavigation.BottomNavigationView
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.observers.DisposableObserver
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subscribers.DisposableSubscriber
import org.reactivestreams.Subscriber
import org.reactivestreams.Subscription
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"

    private var fragment = Fragment()
    private lateinit var bottomPanel: BottomNavigationView

    var isCanGreet = true
    var timeCount = 0

    private val timeCountObservable = Observable.interval(1, TimeUnit.SECONDS)
    private lateinit var timeCountDisposable: DisposableObserver<Long>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()
        countLaunch()
    }

    override fun onResume() {
        super.onResume()
        createNewDisposableAndSubscribe()
    }

    override fun onPause() {
        super.onPause()

        val sp = getPreferences(MODE_PRIVATE)
        var tempTimeCount = sp.getInt(getString(R.string.time_count_key), 0)
        tempTimeCount += timeCount
        with(sp?.edit()) {
            this?.putInt(getString(R.string.time_count_key), tempTimeCount)
            this?.apply()
        }
        timeCountDisposable.dispose()

        Log.d(TAG, "timeCount onPause")
    }

    private fun countLaunch() {
        val sp = getPreferences(MODE_PRIVATE)
        var tempLaunchCount = sp.getInt(getString(R.string.launch_count_key), 0)
        tempLaunchCount++
        with(sp?.edit()) {
            this?.putInt(getString(R.string.launch_count_key), tempLaunchCount)
            this?.apply()
        }
    }

    private fun initViews() {
        bottomPanel = findViewById(R.id.bottom_panel)

        fragment = CurrentWeatherFragment()
        showFragment(fragment, R.id.fragment_container)

        bottomPanel.setOnItemSelectedListener { menu -> setupBottomNavigationMenu(menu) }
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

        R.id.weather_map -> {
            if (fragment !is WeatherMapFragment) {
                fragment = WeatherMapFragment()
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
        Log.d(TAG, "showfragment $fragment")
    }

    private fun createNewDisposableAndSubscribe() {
        timeCountDisposable = object : DisposableObserver<Long>() {

            override fun onNext(t: Long?) {
                Log.d(TAG, "timeCount: $t")
                timeCount++
            }

            override fun onError(t: Throwable?) {
                Log.d(TAG, "timeCount onError: ${t?.message}")
            }

            override fun onComplete() {
                Log.d(TAG, "timeCount onComplete")
            }
        }
        timeCountObservable.subscribe(timeCountDisposable)
    }
}


