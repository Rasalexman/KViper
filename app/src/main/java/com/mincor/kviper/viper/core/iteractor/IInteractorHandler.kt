package com.mincor.kviper.viper.core.iteractor

interface IInteractorHandler {
    fun onErrorHandler()
    fun onResultHandler(result:Any?)
}