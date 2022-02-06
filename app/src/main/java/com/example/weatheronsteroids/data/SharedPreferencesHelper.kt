package com.example.weatheronsteroids.data

import android.content.Context
import android.content.Context.MODE_PRIVATE
import com.example.weatheronsteroids.R
import javax.inject.Inject

class SharedPreferencesHelper @Inject constructor(private val context: Context) {

    private val sharedPreferences =
        context.getSharedPreferences("SharedPreferencesHelper", MODE_PRIVATE)


    fun putTime(tempTimeCount: Int) {
        sharedPreferences.edit().putInt(context.getString(R.string.time_count_key), tempTimeCount)
            .apply()
    }

    fun getTime(): Int {
        return sharedPreferences.getInt(context.getString(R.string.time_count_key), 0)
    }

    fun putLaunch() {
        var tempLaunchCount =
            sharedPreferences.getInt(context.getString(R.string.launch_count_key), 0)
        tempLaunchCount++
        sharedPreferences.edit()
            .putInt(context.getString(R.string.launch_count_key), tempLaunchCount).apply()
    }


    fun getLaunch(): Int {
        return sharedPreferences.getInt(context.getString(R.string.launch_count_key), 0)
    }

    fun setName(name: String) {
        sharedPreferences.edit().putString(context.getString(R.string.user_name_key), name).apply()
    }

    fun getUserName(): String? {
        val name = context.resources.getString(R.string.user_name_key)
        return sharedPreferences.getString(context.getString(R.string.user_name_key), name)
    }

    fun clearName() {
        sharedPreferences.edit().remove(context.getString(R.string.user_name_key)).apply()
    }

    fun getIsCanGreet(): Boolean {
        return sharedPreferences.getBoolean(context.getString(R.string.is_can_greet_key), true)
    }

    fun putIsCanGreet() {
        sharedPreferences.edit().putBoolean(context.getString(R.string.is_can_greet_key), false)
            .apply()
    }

    fun resetIsCanGreet() {
        sharedPreferences.edit().remove(context.getString(R.string.is_can_greet_key)).apply()
    }
}