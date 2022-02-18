package com.example.weatheronsteroids.utils

import android.content.Context
import androidx.annotation.StringRes

fun Context.string(@StringRes res: Int) = resources.getString(res)

