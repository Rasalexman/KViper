package com.mincor.kviper.viper.baseui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bluelinelabs.conductor.Controller
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import com.mincor.kviper.utils.clearAfterDestroyView

abstract class ViewBindController : Controller, KodeinAware {

    override val kodein: Kodein by lazy {
        (applicationContext as KodeinAware).kodein
    }

    protected constructor()
    protected constructor(args: Bundle) : super(args)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup): View {
        val view = getViewInstance(inflater.context)
        onViewCreated(view)
        return view
    }

    open fun onViewCreated(view: View) {}

    abstract fun getViewInstance(context: Context):View

    override fun onDestroyView(view: View) {
        //(getView() as BaseView).clearView()
        super.onDestroyView(view)
        clearAfterDestroyView<ViewBindController>()
    }
}
