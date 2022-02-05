package com.example.weatheronsteroids.data

import android.content.Context
import android.content.Context.MODE_PRIVATE
import com.example.weatheronsteroids.R
import javax.inject.Inject

class SharedPreferencesHelper @Inject constructor(private val context: Context) {

    private val sharedPreferences =
        context.getSharedPreferences("SharedPreferencesHelper", MODE_PRIVATE)


    fun putTime(tempTimeCount: Int) {
        with(sharedPreferences.edit()) {
            this?.putInt(context.getString(R.string.time_count_key), tempTimeCount)
            this?.apply()
        }
    }

    fun getTime(): Int {
        return sharedPreferences.getInt(context.getString(R.string.time_count_key), 0)
    }

    fun putLaunch() {
        var tempLaunchCount =
            sharedPreferences.getInt(context.getString(R.string.launch_count_key), 0)
        tempLaunchCount++
        with(sharedPreferences.edit()) {
            this?.putInt(context.getString(R.string.launch_count_key), tempLaunchCount)
            this?.apply()
        }
    }

    fun getLaunch(): Int {
        return sharedPreferences.getInt(context.getString(R.string.launch_count_key), 0)
    }

    fun setName(name: String) {
        with(sharedPreferences.edit()) {
            this?.putString(context.getString(R.string.user_name_key), name)
            this?.apply()
        }
    }

    fun getUserName(): String? {
        val name = context.resources.getString(R.string.user_name_key)
        return sharedPreferences.getString(context.getString(R.string.user_name_key), name)
    }

    fun clearName() {
        with(sharedPreferences.edit()) {
            this?.remove(context.getString(R.string.user_name_key))
            this?.apply()
        }
    }
}