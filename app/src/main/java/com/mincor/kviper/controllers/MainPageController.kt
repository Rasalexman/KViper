package com.mincor.kviper.controllers

import android.content.Context
import android.view.View
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.mikepenz.fastadapter.items.AbstractItem
import com.mincor.kviper.di.contracts.IMainPageContract
import com.mincor.kviper.viper.baseui.BaseRecyclerController
import com.mincor.weatherme.R
import org.jetbrains.anko.AnkoComponent
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.swipeRefreshLayout
import org.kodein.di.generic.instance

class MainPageController : BaseRecyclerController(), IMainPageContract.IView {

    override val presenter: IMainPageContract.IPresenter by instance()

    override fun getViewInstance(context: Context): View = createView(AnkoContext.create(context, this))

    private var refreshLayout: SwipeRefreshLayout? = null

    override fun onViewCreated(view: View) {
        presenter.bind(this)
    }

    override fun onSuccess(list: List<AbstractItem<*, *>>) {
        hideLoading()
        mFastItemAdapter?.add(list)
    }

    private fun onRefreshLayout() {
        refreshLayout?.isRefreshing = false
    }

    override fun onDestroyView(view: View) {
        super.onDestroyView(view)
        presenter.unbind()
    }

    override fun onError(error: String) {

    }

    companion object : AnkoComponent<MainPageController> {
        override fun createView(ui: AnkoContext<MainPageController>): View = with(ui) {
            ui.owner.refreshLayout = swipeRefreshLayout {
                id = R.id.main_refresh_layout_id

                //----- ЛИСТ С РЕЗУЛЬТАТАМИ ПОИСКА
                ui.owner.recycler = recyclerView {
                    id = R.id.rv_controller
                    lparams(org.jetbrains.anko.matchParent, org.jetbrains.anko.matchParent)
                }

                onRefresh {
                    ui.owner.onRefreshLayout()

                }
            }
            ui.owner.refreshLayout!!
        }
    }
}