package com.example.weatheronsteroids.navigation

import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.Cicerone.Companion.create

class LocalCiceroneHolder {

    private val containers = HashMap<String, Cicerone<Router>>()

    fun getCicerone(containterTag: String): Cicerone<Router> =
        containers.getOrPut(containterTag) {
            create()
        }

}