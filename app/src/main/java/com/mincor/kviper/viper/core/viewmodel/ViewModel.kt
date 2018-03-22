package com.mincor.kviper.viper.core.viewmodel

import com.mincor.kviper.viper.core.contracts.IIteratorContact
import com.mincor.kviper.viper.core.contracts.IViewModelContract
import com.mincor.kviper.viper.core.iteractor.Iteractor
import javax.swing.text.html.HTML.Tag.I



/**
 * Created by alexander on 22.03.2018.
 */
abstract class ViewModel<out T:Iteractor<IDC>, IDC> : IViewModelContract, IIteratorContact {

    /**
     * An instance of [Interactor] type specific for this ViewModel instance.
     */
    private val mInteractor: T? = null

    override fun onViewModelCreated() {
        //this.mInteractor = initInteractor()
        //this.mInteractor.bind(this)
        //this.mInteractor.init()
    }
}