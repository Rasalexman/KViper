package com.mincor.kviper.viper.core.contracts

import com.mincor.kviper.viper.core.viewmodel.ViewModel

/**
 * Created by a.minkin on 06.03.2018.
 */
interface IPresenterContract<out T : ViewModel> {

    fun initViewModel():T
    fun onPresenterDetached(wasDestroyed: Boolean)
    fun onPresenterAttached(firstAttachment: Boolean)

}