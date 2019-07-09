package com.mincor.kviper.controllers

import android.graphics.Color
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.widget.SearchView
import com.mikepenz.fastadapter.items.AbstractItem
import com.mincor.kviper.di.contracts.ISearchWeatherContract
import com.mincor.kviper.viper.baseui.BaseActionBarRecyclerController
import com.mincor.weatherme.R
import org.jetbrains.anko.*
import org.jetbrains.anko.appcompat.v7.toolbar
import org.jetbrains.anko.design.appBarLayout
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.kodein.di.generic.instance

class SearchWeatherController : BaseActionBarRecyclerController(), SearchView.OnQueryTextListener, ISearchWeatherContract.IView {

    override val presenter: ISearchWeatherContract.IPresenter by instance()

    //override fun getViewInstance(context: Context): View = SearchWeatherUI().createView(AnkoContext.create(context, this))

    override var title: String = ""
        get() = resources?.getString(R.string.weather_search_title)?:""

    init {
        activateOptionsMenu()
    }

    // строка поиска которой назначаем слушатель
    private var searchView: SearchView? = null
    //
    private var searchInput: EditText? = null
    //
    private var search: MenuItem? = null

    override fun onAttach(view: View) {
        super.onAttach(view)
        presenter.bind(this)

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        // очищаем меню от уже ранее добавленных элементов
        menu.clear()
        // создаем новое меню
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.search_menu, menu)
        search = menu.findItem(R.id.app_bar_search)

        searchView = search!!.actionView as SearchView
        // назначаем слушатель изменения текста
        searchView!!.setOnQueryTextListener(this)
        searchView!!.setIconifiedByDefault(false)
        val searchIcon = searchView!!.find(R.id.search_mag_icon) as ImageView
        searchIcon.setImageDrawable(null)

        searchInput = searchView!!.find(R.id.search_src_text)
        searchInput!!.setTextColor(Color.WHITE)
        searchInput!!.setHintTextColor(Color.parseColor("#70d5d5d5"))
        searchInput!!.hint = resources?.getString(R.string.search_weather_hint)
        searchInput!!.setTextColor(Color.WHITE)
        searchInput!!.setHintTextColor(Color.WHITE)

        toolbar?.onClick {
            // проверяем нужно ли раскрыавть список при появлении
            if(search?.isActionViewExpanded == false) search?.expandActionView()
        }
    }

    //////---- UI REQUESTS
    override fun onQueryTextSubmit(query: String): Boolean {
        searchView?.clearFocus()
        presenter.onSearchSubmit(query)
        return true
    }

    override fun onQueryTextChange(newText: String): Boolean = true


    ////---------- PRESENTER RESULTS
    override fun onError(error: String) {

    }

    override fun onSuccess(searchList: List<AbstractItem<*, *>>) {
        mFastItemAdapter?.add(searchList)
    }

    override fun onDetach(view: View) {
        presenter.unbind()
        toolbar?.setOnClickListener(null)

        searchView?.setOnQueryTextListener(null)
        searchView?.setOnSearchClickListener(null)
        searchView = null
        searchInput?.text = null
        searchInput = null
        search = null
        super.onDetach(view)
    }

    class SearchWeatherUI : AnkoComponent<SearchWeatherController>{
        override fun createView(ui: AnkoContext<SearchWeatherController>): View = with(ui){
            relativeLayout {
                lparams(org.jetbrains.anko.matchParent, org.jetbrains.anko.matchParent)
                appBarLayout {
                    ui.owner.toolbar = toolbar {
                        id = R.id.main_toolbar
                        setTitleTextColor(Color.WHITE)
                    }.lparams(matchParent, dip(56))
                }.lparams(matchParent)
            }
        }
    }
}