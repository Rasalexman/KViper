package com.mincor.kviper.viper.core.contracts


/**
 * Created by alexander on 22.03.2018.
 */
interface IViewModelContract {

    fun onViewModelCreated()
    fun onViewModelRemoved()


    fun onViewModelAttached(firstAttachment: Boolean)
    fun onViewModelDetached(wasDestroyed: Boolean)
    fun initInteractor()
}