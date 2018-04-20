package com.mincor.kviper.viper.baseui

import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.view.View

/**
 * Created by alexander on 24.08.17.
 */

abstract class BaseActionBarController : BaseController() {

    protected var toolbar: Toolbar? = null

    override fun onAttach(view: View) {
        super.onAttach(view)
        setActionBar()
        setTitle()
    }

    //https://github.com/holgerbrandl/themoviedbapi/

    protected fun setHomeButtonEnable() {
        //set the back arrow in the toolbar
        actionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onDestroy() {
        super.onDestroy()
        toolbar = null
    }

    //handle the click on the back arrow click
    override fun onOptionsItemSelected(item: MenuItem): Boolean =
            when (item.itemId) {
                android.R.id.home -> {
                    goBack()
                    true
                }
                else -> super.onOptionsItemSelected(item)
            }

    protected open fun goBack() {
        router.popController(this)
    }

    override fun getToolBar(): Toolbar? = toolbar
}
