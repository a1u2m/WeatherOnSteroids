package com.example.weatheronsteroids.ui.base

import com.arellomobile.mvp.MvpPresenter
import io.reactivex.rxjava3.disposables.CompositeDisposable

abstract class BaseMvpPresenter<T : BaseMvpView> : MvpPresenter<T>() {

    protected val disposables = CompositeDisposable()

    override fun onDestroy() {
        super.onDestroy()
        disposables.clear()
    }

}