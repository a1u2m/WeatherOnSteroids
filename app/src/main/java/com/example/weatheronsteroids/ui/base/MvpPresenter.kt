package com.example.weatheronsteroids.ui.base

interface MvpPresenter<V: BaseMvpView> {

    fun attachView(mvpView: V)

    fun viewIsReady()

    fun detachView()

    fun destroy()

}