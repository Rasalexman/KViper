package com.mincor.kviper.adapters

import android.content.Context
import android.graphics.Color
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.items.AbstractItem
import com.mincor.kviper.R
import com.mincor.kviper.utils.roundedBg
import org.jetbrains.anko.*
import ru.fortgroup.dpru.adapters.IDataHolder

/**
 * Created by Alex on 07.01.2017.
 */

class DoubleCircleItem() : AbstractItem<DoubleCircleItem, DoubleCircleItem.ViewHolder>(), IDataHolder {

    override fun createView(ctx: Context, parent: ViewGroup?): View = NextItemUI().createView(AnkoContext.create(ctx, this))
    override fun getViewHolder(v: View): ViewHolder = ViewHolder(v)
    override fun getType(): Int = R.id.double_c_item_id
    override fun getLayoutRes(): Int = -1

    /**
     * Событие когда кликнули на тэг
     */
    /*class TagMainClickEvent : ClickEventHook<MainItem>() {
        override fun onBind(viewHolder: RecyclerView.ViewHolder): View? = (viewHolder as? ViewHolder?)?.mainTag
        override fun onClick(v: View?, position: Int, fastAdapter: FastAdapter<MainItem>?, item: MainItem) {
            KDispatcher.call(TAG_IN_ITEM_CLICK_EVENT, item)
            // analytics
            Analytics.sendAnalytics(Analytics.TAG_CLICKED, bundleOf(Pair(Consts.NAME, item.tagName
                    ?: ""), Pair(Consts.SCREEN, MenuScreens.SCREEN_MAIN.tag)))
        }
    }*/

    /**
     * Событие когда кликнули на тэг
     */
    /*class AuthorMainClickEvent : ClickEventHook<MainItem>() {
        override fun onBind(viewHolder: RecyclerView.ViewHolder): View? = (viewHolder as? ViewHolder?)?.mainAuthor
        override fun onClick(v: View?, position: Int, fastAdapter: FastAdapter<MainItem>?, item: MainItem) {
            KDispatcher.call(AUTHOR_IN_ITEM_CLICK_EVENT, item.author)
        }
    }*/

    /**
     * our ViewHolder
     */
    class ViewHolder(view: View) : FastAdapter.ViewHolder<DoubleCircleItem>(view) {
        /*val mainAuthor: TextView = view.find(R.id.text_main_author)
        val mainTag: TextView = view.find(R.id.text_main_rubric)

        private val mainImage: ImageView = view.find(R.id.image_main)
        private val imageProgress: ProgressBar = view.find(R.id.image_loader)
        private val mainTitle: TextView = view.find(R.id.text_main_title)*/

        override fun bindView(item: DoubleCircleItem, payloads: MutableList<Any>?) {
            /*mainAuthor.text = item.author
            mainTitle.text = item.title.fromHTML(mainTitle.context)

            if (item.tagName.isNullOrEmpty()) {
                mainTag.hide()
            } else {
                mainTag.show()
                mainTag.text = item.tagName
            }

            if (item.imgUrl.isNotEmpty()) {
                mainImage.load(item.imgUrl, imageProgress)
            }*/
        }

        override fun unbindView(item: DoubleCircleItem?) {
           /* mainImage.clear()
            mainAuthor.text = null
            mainTitle.text = null
            mainTag.text = null*/
        }
    }

    inner class NextItemUI : AnkoComponent<DoubleCircleItem> {
        override fun createView(ui: AnkoContext<DoubleCircleItem>): View = with(ui) {
            frameLayout {
                //backgroundColor = Color.parseColor("#7987C8")//color(R.color.colorRed)
                id = R.id.main_item_lay
                lparams(matchParent)

                relativeLayout {
                    background = roundedBg(Color.parseColor("#7582C1"), 16f)
                    id = R.id.rounded_bg
                }.lparams(matchParent, (context.displayMetrics.widthPixels / 16) * 9) {
                    setMargins(dip(16),dip(16), dip(16), dip(16))
                }

                ////----- TOP
                view {
                    background = roundedBg(Color.parseColor("#1B1B1B")) //
                }.lparams(dip(24), dip(24)) {
                    gravity = Gravity.TOP
                    setMargins(dip(36),  dip(8),0, 0)
                }

                view {
                    background = roundedBg(Color.WHITE) //
                }.lparams(dip(16),dip(16)) {
                    gravity = Gravity.TOP
                    setMargins(dip(40),  dip(12),0, 0)
                }

                view {
                    backgroundColor = Color.WHITE
                }.lparams(dip(2), dip(16)) {
                    gravity = Gravity.TOP
                    setMargins(dip(47),  0,0, 0)
                }


                /// BOTTOM
                view {
                    background = roundedBg(Color.parseColor("#1B1B1B")) //
                }.lparams(dip(24), dip(24)) {
                    gravity = Gravity.BOTTOM
                    setMargins(dip(36),  0,0, dip(8))
                }

                view {
                    background = roundedBg(Color.WHITE) //
                }.lparams(dip(16),dip(16)) {
                    gravity = Gravity.BOTTOM
                    setMargins(dip(40),  0,0, dip(12))
                }


                view {
                    backgroundColor = Color.WHITE
                }.lparams(dip(2), dip(16)) {
                    gravity = Gravity.BOTTOM
                    setMargins(dip(47),  0,0, 0)
                }



                //#7582C1
                /*imageView {
                    id = R.id.image_main
                    scaleType = ImageView.ScaleType.CENTER_CROP
                }.lparams(matchParent, matchParent)

                view {
                    id = R.id.main_item_darkener_id
                    backgroundResource = R.drawable.image_gradient
                }.lparams(matchParent, matchParent)

                progressBar {
                    id = R.id.image_loader
                    indeterminateDrawable = drawable(R.drawable.spinner_ring_white)
                    hide(true)
                }.lparams(dip(24), dip(24)) {
                    centerInParent()
                }

                textView {
                    id = R.id.text_main_author
                    textColor = Color.WHITE
                    textSize = FontSettings.getFontSize(Consts.FONT_SIZE_AUTHOR)
                    typeface = Typeface.DEFAULT_BOLD
                }.lparams(matchParent) {
                    alignParentBottom()
                    setMargins(dip(16), 0, dip(16), dip(16))
                }

                textView {
                    id = R.id.text_main_title
                    textColor = Color.WHITE
                    textSize = FontSettings.getFontSize(Consts.FONT_SIZE_TITLE)
                    setTypeface(Typeface.DEFAULT, Typeface.BOLD)
                }.lparams(matchParent) {
                    above(R.id.text_main_author)
                    setMargins(dip(16), dip(8), dip(16), dip(8))
                }

                textView {
                    textSize = FontSettings.getFontSize(Consts.FONT_SIZE_TAG)
                    id = R.id.text_main_rubric
                    textColor = Color.WHITE
                    backgroundColor = color(R.color.colorRed)
                    leftPadding = dip(8)
                    topPadding = dip(2)
                    rightPadding = dip(8)
                    bottomPadding = dip(3)
                    typeface = Typeface.DEFAULT_BOLD
                }.lparams {
                    above(R.id.text_main_title)
                    setMargins(dip(16), dip(8), dip(16), 0)
                }*/
            }
        }
    }

}
