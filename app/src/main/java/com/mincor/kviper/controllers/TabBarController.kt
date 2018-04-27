package com.mincor.kviper.controllers

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.support.design.widget.TabLayout
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.bluelinelabs.conductor.Controller
import com.bluelinelabs.conductor.Router
import com.mincor.kviper.R
import com.mincor.kviper.utils.color
import com.mincor.kviper.utils.string
import com.mincor.kviper.utils.wdthProc
import com.mincor.kviper.viper.baseui.BaseController
import org.jetbrains.anko.*
import org.jetbrains.anko.design.tabLayout

/**
 * Created by a.minkin on 02.10.2017.
 */
class TabBarController : BaseController(), TabLayout.OnTabSelectedListener {

    private var tabLayout: TabLayout? = null
    private var mainLayout: RelativeLayout? = null

    private var mPosition: Int = 0                           // текущая позиция
    private var currentSelectedTF: TextView? = null          // текущий выьранный текст филд
    private var currentSelectedImg: ImageView? = null        // текущая выбранная картинка
    private var selectedTab: TabLayout.Tab? = null           // выбранный таб

    private var mainSceneRouter: Router? = null              // главный роутер который переключает контроллеры
    private var currentController: Controller? = null

    override fun getViewInstance(context: Context) = TabBarUI().createView(AnkoContext.create(context, this))

    private val iconsIds = listOf(R.drawable.ic_tab_one_24dp, R.drawable.ic_list_white_24dp, R.drawable.ic_search_white_24dp, R.drawable.ic_map_white_24dp, R.drawable.ic_account_box_white_24dp)
    //private val unSelectedIcons = listOf(R.drawable.ic_tab_bar_main_stroked_20, R.drawable.ic_tab_bar_news_stroked_20, R.drawable.ic_tab_bar_search_stroked_20, R.drawable.ic_tab_bar_bookmark_stroked_20, R.drawable.ic_tab_bar_menu_stroked_20)

    override fun onAttach(view: View) {
        super.onAttach(view)

        mainSceneRouter = mainSceneRouter ?: this.getChildRouter(mainLayout!!)
        mainSceneRouter!!.setPopsLastView(false)

        if (!mainSceneRouter!!.hasRootController()) {
            onTabSelected(tabLayout!!.getTabAt(mPosition)!!)
        } else {
           setSelectedTab(tabLayout!!.getTabAt(mPosition)!!)
        }
    }

    override fun onDetach(view: View) {
        currentSelectedTF = null
        currentSelectedImg = null
        selectedTab = null
        super.onDetach(view)
    }

    override fun onDestroy() {
        super.onDestroy()
        tabLayout?.clearOnTabSelectedListeners()
        tabLayout = null
        mainLayout?.removeAllViews()
        mainLayout = null
        mainSceneRouter = null
    }

    override fun onTabSelected(tab: TabLayout.Tab) {
        if (selectedTab != tab) {
            setSelectedTab(tab)

            var tag: String = ""
            // Выбираем текущий контроллер для показа
            /*currentController =
                    when (mPosition) {
                        MenuScreens.SCREEN_MAIN.screenID -> {
                            tag = MenuScreens.SCREEN_MAIN.tag
                            MainPageController()
                        }
                        MenuScreens.SCREEN_NEWS.screenID -> {
                            tag = MenuScreens.SCREEN_NEWS.tag
                            val isEmptyTag = selectedTagGuid.isEmpty()
                            NewsPageController(selectedTagGuid, isEmptyTag)
                        }
                        MenuScreens.SCREEN_SEARCH.screenID -> {
                            tag = MenuScreens.SCREEN_SEARCH.tag
                            SearchPageController(selectedSearchStr)
                        }
                        MenuScreens.SCREEN_BOOKMARKS.screenID -> {
                            tag = MenuScreens.SCREEN_BOOKMARKS.tag
                            BookmarksPageController()
                        }
                        MenuScreens.SCREEN_MENU.screenID -> {
                            tag = MenuScreens.SCREEN_MENU.tag
                            MenuPageController()
                        }
                        else -> null
                    }

            // если контроллер выбран показываем его
            currentController?.let {
                Analytics.sendAnalytics(Analytics.SCREEN_SHOWED, bundleOf(Pair(Consts.SCREEN, tag)))
                mainSceneRouter?.setRoot(RouterTransaction.with(it)) //.tag(tag)
            }*/
        }
    }

    override fun onTabUnselected(tab: TabLayout.Tab) {}
    override fun onTabReselected(tab: TabLayout.Tab) {}

    //----- UTILS SECTION ---///
    private fun setSelectedTab(tab: TabLayout.Tab) {
        applicationContext?.let {
            currentSelectedTF?.setTypeface(null, Typeface.NORMAL)
            //currentSelectedImg?.setImageResource(unSelectedIcons[mPosition])

            // TODO: NEED TO CHANGE findViewById
            currentSelectedTF = tab.customView!!.find(R.id.tab_header)
            currentSelectedTF?.setTypeface(null, Typeface.BOLD)

            currentSelectedImg = tab.customView!!.find(R.id.tab_icon)
            //currentSelectedImg?.setImageResource(selectedIcons[tab.position])

            selectedTab = tab
            mPosition = tab.position
        }
    }

    override fun handleBack(): Boolean = mainSceneRouter?.handleBack() ?: true

    private fun createTab(newTab: TabLayout.Tab, context: Context, resId: Int, menuName: String): TabLayout.Tab =
            newTab.apply {
                customView = with(context) {
                    verticalLayout {
                        val wdth = wdthProc(1.0f)
                        gravity = Gravity.CENTER
                        imageView {
                            id = R.id.tab_icon
                            setImageResource(resId)
                        }.lparams(dip(20), dip(20)) {
                            //centerHorizontally()
                            topMargin = dip(2)
                        }
                        textView {
                            id = R.id.tab_header
                            textSize = if (wdth < 500) 9f else 10f
                            textColor = Color.WHITE
                            gravity = Gravity.CENTER_HORIZONTAL
                            text = menuName
                        }.lparams(matchParent) {
                            //below(R.guid.tab_icon)
                            topMargin = dip(2)
                        }
                    }
                }
            }

    inner class TabBarUI : AnkoComponent<TabBarController> {
        override fun createView(ui: AnkoContext<TabBarController>): View = with(ui) {
            verticalLayout {
                lparams(matchParent, matchParent)

                mainLayout = relativeLayout {
                    id = R.id.main_lay
                    backgroundColor = color(R.color.colorBackground)
                }.lparams(matchParent, matchParent) {
                    weight = 2f
                }

                tabLayout = tabLayout {
                    id = R.id.main_tab
                    tabMode = TabLayout.MODE_FIXED
                    //backgroundColor = color(R.color.colorRed)
                    //setSelectedTabIndicatorColor(color(R.color.colorRed))

                    addTab(createTab(this.newTab(), context, iconsIds[0], string(R.string.menu_weather)))
                    addTab(createTab(this.newTab(), context, iconsIds[1], string(R.string.menu_list)))
                    addTab(createTab(this.newTab(), context, iconsIds[2], string(R.string.menu_search)))
                    addTab(createTab(this.newTab(), context, iconsIds[3], string(R.string.menu_map)))
                    addTab(createTab(this.newTab(), context, iconsIds[4], string(R.string.menu_menu)))

                    addOnTabSelectedListener(this@TabBarController)
                }.lparams(matchParent, dip(72)) {
                    weight = 1f
                }
            }
        }
    }

}