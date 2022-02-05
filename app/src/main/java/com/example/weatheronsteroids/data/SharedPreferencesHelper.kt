package com.example.weatheronsteroids.data

import android.content.Context.MODE_PRIVATE
import androidx.appcompat.app.AppCompatActivity
import com.example.weatheronsteroids.R
import com.example.weatheronsteroids.ui.main.MainActivity

class SharedPreferencesHelper(private val activity: AppCompatActivity) {

    private val sharedPreferences = activity.getPreferences(MODE_PRIVATE)

    private fun putTime(tempTimeCount: Int) {
        with(sharedPreferences.edit()) {
            this?.putInt(activity.getString(R.string.time_count_key), tempTimeCount)
            this?.apply()
        }
    }

    fun putLaunch() {
        var tempLaunchCount = sharedPreferences.getInt(activity.getString(R.string.launch_count_key), 0)
        tempLaunchCount++
        with(sharedPreferences.edit()) {
            this?.putInt(activity.getString(R.string.launch_count_key), tempLaunchCount)
            this?.apply()
        }
    }

    fun countTime() {
        var tempTimeCount = sharedPreferences.getInt(activity.getString(R.string.time_count_key), 0)
        tempTimeCount += (activity as MainActivity).timeCount
        putTime(tempTimeCount)
        activity.timeCountDisposable.dispose()
    }
}