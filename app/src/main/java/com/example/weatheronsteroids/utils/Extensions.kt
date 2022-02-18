package com.example.weatheronsteroids.utils

import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes
import com.example.weatheronsteroids.R

fun Context.string(@StringRes res: Int) = resources.getString(res)

fun showError(context: Context) = Toast.makeText(
    context, context.string(
        R.string.error
    ), Toast.LENGTH_LONG
).show()