package com.mincor.kviper.viper.core.iteractor

interface IInteractor<T : IInteractorHandler> {
    var output:T?
    fun unbind()
}