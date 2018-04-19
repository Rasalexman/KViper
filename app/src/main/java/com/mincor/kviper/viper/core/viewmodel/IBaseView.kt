package com.mincor.kviper.viper.core.viewmodel

import com.mincor.kviper.viper.core.presenter.IBasePresenter

/**
 * Created by a.minkin on 25.10.2017.
 */
interface IBaseView<out T : IBasePresenter<*>> {
    val presenter: T
}