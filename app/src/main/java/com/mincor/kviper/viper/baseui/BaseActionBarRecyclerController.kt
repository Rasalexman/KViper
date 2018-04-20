package com.mincor.kviper.viper.baseui

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.view.View
import com.bluelinelabs.conductor.ControllerChangeHandler
import com.bluelinelabs.conductor.ControllerChangeType
import com.mincor.kviper.R
import org.jetbrains.anko.*
import org.jetbrains.anko.appcompat.v7.toolbar
import org.jetbrains.anko.design.appBarLayout
import org.jetbrains.anko.recyclerview.v7.recyclerView

/**
 * Created by a.minkin on 20.10.2017.
 */
open class BaseActionBarRecyclerController : BaseRecyclerController {

    protected constructor()
    protected constructor(args: Bundle) : super(args)

    protected var toolbar: Toolbar? = null

    override fun getViewInstance(context: Context):View = ToolbarRecyclerUI().createView(AnkoContext.create(context, this))

    override fun onAttach(view: View) {
        super.onAttach(view)
        setActionBar()
        setTitle()
    }

    override fun onChangeStarted(changeHandler: ControllerChangeHandler, changeType: ControllerChangeType) {
        super.onChangeStarted(changeHandler, changeType)
        setOptionsMenuHidden(!changeType.isEnter)
    }

    protected fun setHomeButtonEnable() {
        //set the back arrow in the toolbar
        actionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        android.R.id.home -> {
            goBack()
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    open fun goBack() {
        router.popController(this)
    }

    override fun getToolBar(): Toolbar? = toolbar

    override fun onDestroy() {
        toolbar = null
        super.onDestroy()
    }

    inner class ToolbarRecyclerUI : AnkoComponent<BaseActionBarRecyclerController>{
        override fun createView(ui: AnkoContext<BaseActionBarRecyclerController>): View = with(ui){
            verticalLayout {
                lparams(org.jetbrains.anko.matchParent, org.jetbrains.anko.matchParent)
                appBarLayout {
                    toolbar = toolbar {
                        id = R.id.main_toolbar
                        setTitleTextColor(Color.WHITE)
                    }.lparams(matchParent, dip(56))
                }.lparams(matchParent)

                //----- ЛИСТ С РЕЗУЛЬТАТАМИ ПОИСКА
                recycler = recyclerView {
                    id = R.id.rv_controller
                    lparams(org.jetbrains.anko.matchParent, org.jetbrains.anko.matchParent)
                }
            }
        }
    }
}