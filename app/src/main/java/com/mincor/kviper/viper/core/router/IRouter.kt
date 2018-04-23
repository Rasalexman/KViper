package com.mincor.kviper.viper.core.router

interface IRouter<T> {
    var view: T?
    fun unbind()
}