package com.mincor.kviper.viper.core.iteractor

import com.mincor.kviper.viper.core.contracts.IIteratorContact

/**
 * Created by a.minkin on 06.03.2018.
 */
abstract class Iteractor<out T> : IIteratorContact {
    abstract val iteractorContract:IIteratorContact

    private val dataContractIterator:T = initDataContract()

    abstract fun initDataContract():T

}