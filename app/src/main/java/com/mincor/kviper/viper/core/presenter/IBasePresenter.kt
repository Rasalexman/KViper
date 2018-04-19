package com.mincor.kviper.viper.core.presenter

/**
 * Created by a.minkin on 25.10.2017.
 */
interface IBasePresenter<T> {
    fun start()
    fun stop()
    var view: T?
}