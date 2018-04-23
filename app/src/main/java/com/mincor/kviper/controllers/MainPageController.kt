package com.mincor.kviper.controllers

import android.view.View
import com.mincor.kviper.di.contracts.IMainPageContract
import com.mincor.kviper.viper.baseui.BaseActionBarRecyclerController
import org.kodein.di.generic.instance

class MainPageController : BaseActionBarRecyclerController(), IMainPageContract.IView {

    override val presenter: IMainPageContract.IPresenter by instance()


    override fun onAttach(view: View) {
        super.onAttach(view)
        presenter.bind(this)
    }

    override fun onDetach(view: View) {
        super.onDetach(view)
        presenter.unbind()
    }

    override fun onError(error: String) {

    }
}