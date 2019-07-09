package com.mincor.kviper.viper.baseui

import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.appcompat.widget.Toolbar
import android.view.View
import com.mincor.kviper.viper.baseui.actionbar.ActionBarProvider

abstract class BaseController : ViewBindController {

    protected constructor()
    protected constructor(args: Bundle) : super(args)

    override fun onAttach(view: View) {
        super.onAttach(view)
        attachListeners()
    }

    override fun onDetach(view: View) {
        detachListeners()
        super.onDetach(view)
    }

    /**
     * Назначаем слушателей для текущего Контроллера
     */
    protected open fun attachListeners() {}

    /**
     * Удаляем слушателей для текущего контроллера
     */
    protected open fun detachListeners() {}


    /***
     * Все что касается хранения и обработки тулбара
     */
    protected val actionBar: ActionBar?
        get() {
            val actionBarProvider = activity as ActionBarProvider?
            return actionBarProvider?.getSupportActionBar()
        }

    protected fun setActionBar() {
        getToolBar()?.let {
            (activity as? ActionBarProvider)?.setSupportActionBar(it)
        }
    }

    protected fun activateOptionsMenu() {
        super.setHasOptionsMenu(true)
    }

    protected fun setTitle() {
        getToolBar()?.title = title
    }

    protected open fun getToolBar(): Toolbar? = null
    protected open var title: String = ""
}
