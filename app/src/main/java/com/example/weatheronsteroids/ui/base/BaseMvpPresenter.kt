package com.example.weatheronsteroids.ui.base

abstract class BaseMvpPresenter<T : BaseMvpView> : MvpPresenter<T> {

    var view: T? = null
        set(value) {
            if (value != null) {
                view?.let { attachView(it) }
            }
            field = value
        }

    override fun attachView(mvpView: T) {
        view = mvpView
    }

    override fun viewIsReady() {
        //not implemented yet
    }

    override fun detachView() {
        view = null
    }

    override fun destroy() {
        //not implemented yet
    }

//    fun onBackCommandClick() {
//        router.exit()
//    }
//
//    fun onForwardCommandClick() {
//        router.navigateTo(Sample(screenNumber + 1))
//    }
//
//    fun onReplaceCommandClick() {
//        router.replaceScreen(Sample(screenNumber + 1))
//    }
//
//    init {
//        viewState?.setTitle("Screen $screenNumber")
//    }
}