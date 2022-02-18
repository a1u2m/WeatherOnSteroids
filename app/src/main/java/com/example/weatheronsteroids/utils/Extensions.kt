package com.example.weatheronsteroids.utils

import android.content.Context
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import com.example.weatheronsteroids.R
import com.squareup.picasso.Picasso

fun Context.string(@StringRes res: Int) = resources.getString(res)

fun showError(context: Context) = Toast.makeText(
    context, context.string(
        R.string.error
    ), Toast.LENGTH_LONG
).show()

fun AppCompatTextView.gone() {
    visibility = View.GONE
}

fun ProgressBar.gone() {
    visibility = View.GONE
}

fun AppCompatImageView.load(pictureLink: String, icon: AppCompatImageView) {
    Picasso.get()
        .load(pictureLink)
        .error(R.drawable.ic_weather_placeholder)
        .into(icon)
}