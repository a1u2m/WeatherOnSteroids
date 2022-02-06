package com.example.weatheronsteroids.ui.main

import androidx.fragment.app.Fragment
import com.example.weatheronsteroids.ui.base.BaseMvpView

interface MainView: BaseMvpView {

    fun countLaunch()

    fun init()

    fun showFragment(fragment: Fragment, id: Int)

}