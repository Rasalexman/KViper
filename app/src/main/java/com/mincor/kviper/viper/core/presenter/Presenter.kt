package com.mincor.kviper.viper.core.presenter

import android.support.annotation.NonNull
import com.mincor.kviper.viper.core.contracts.IPresenterContract
import com.mincor.kviper.viper.core.viewmodel.ViewModel



/**
 * Created by alexander on 22.03.2018.
 */
abstract class Presenter<VM : ViewModel> : IPresenterContract<VM> {

    private var mViewModel: VM? = null

    //private var mWrapper: ViewWrapper<VB, out Presenter>? = null

    private var firstAttachment = true

    fun onPresenterCreated() {
        this.mViewModel = initViewModel()
        //this.mViewModel!!.bind(mWrapper)
        //this.mViewModel!!.onViewModelCreated()
    }

    override fun onPresenterAttached(firstAttachment: Boolean) {
       // this.mViewModel!!.onViewModelAttached(firstAttachment)
    }

    override fun onPresenterDetached(wasDestroyed: Boolean) {
        if (firstAttachment) {
            firstAttachment = false
        }

        //this.mViewModel!!.onViewModelDetached(wasDestroyed)
    }

    fun onPresenterRemoved() {
        //this.mViewModel!!.onViewModelRemoved()
    }


    fun onResume() {}
    fun onPause() {}
    fun isFirstAttachment(): Boolean {
        return firstAttachment
    }

    /*fun getContext(): Context {
        return mWrapper!!.getContext()
    }*/



   /* fun getBinding(): VB {
        return mWrapper!!.getBinding()
    }*/


    fun getViewModel(): VM? {
        return this.mViewModel
    }

    /*fun bind(viewWrapper: ViewWrapper<VB, out Presenter>) {
        this.mWrapper = viewWrapper

        if (this.mViewModel != null)
            this.mViewModel!!.bind(viewWrapper)
    }*/
}