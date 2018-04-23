package com.mincor.kviper.viper.core.iteractor

interface IInteractor<out T : IInteractorHandler> {
    val output:T?
    fun unbind()
}