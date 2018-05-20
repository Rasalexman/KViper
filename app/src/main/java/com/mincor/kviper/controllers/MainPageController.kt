package com.mincor.kviper.controllers

import android.content.Context
import android.support.v4.widget.SwipeRefreshLayout
import android.view.View
import com.mikepenz.fastadapter.items.AbstractItem
import com.mincor.weatherme.R
import com.mincor.kviper.di.contracts.IMainPageContract
import com.mincor.kviper.viper.baseui.BaseRecyclerController
import org.jetbrains.anko.AnkoComponent
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.swipeRefreshLayout
import org.kodein.di.generic.instance

class MainPageController : BaseRecyclerController(), IMainPageContract.IView {

    override val presenter: IMainPageContract.IPresenter by instance()

    override fun getViewInstance(context: Context): View = MainPageUI().createView(AnkoContext.create(context, this))

    private var refreshLayout: SwipeRefreshLayout? = null

    override fun onAttach(view: View) {
        super.onAttach(view)
        presenter.bind(this)
    }

    override fun onSuccess(list: List<AbstractItem<*, *>>) {
        hideLoadingFooter()
        mFastItemAdapter?.add(list)
    }

    private fun onRefreshLayout() {
        refreshLayout?.isRefreshing = false
    }

    override fun onDetach(view: View) {
        super.onDetach(view)
        presenter.unbind()
    }

    override fun onError(error: String) {

    }

    inner class MainPageUI : AnkoComponent<MainPageController> {
        override fun createView(ui: AnkoContext<MainPageController>): View = with(ui) {
            refreshLayout = swipeRefreshLayout {
                id = R.id.main_refresh_layout_id

                //----- ЛИСТ С РЕЗУЛЬТАТАМИ ПОИСКА
                recycler = recyclerView {
                    id = R.id.rv_controller
                    lparams(org.jetbrains.anko.matchParent, org.jetbrains.anko.matchParent)
                }

                onRefresh {
                    onRefreshLayout()

                }
            }
            refreshLayout!!
        }
    }
}