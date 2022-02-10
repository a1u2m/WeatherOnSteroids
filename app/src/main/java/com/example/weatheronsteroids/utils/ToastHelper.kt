package com.example.weatheronsteroids.utils

import android.content.Context
import android.widget.Toast
import com.example.weatheronsteroids.R
import javax.inject.Inject

class ToastHelper @Inject constructor(val context: Context) {

    val res = context.resources

    fun showErrorToast() {
        Toast.makeText(context, res.getString(R.string.error), Toast.LENGTH_LONG).show()
    }
}