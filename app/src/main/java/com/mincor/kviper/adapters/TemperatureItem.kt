package com.mincor.kviper.adapters

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.items.AbstractItem
import com.mincor.kviper.utils.roundedBg
import com.mincor.weatherme.R
import org.jetbrains.anko.*
import ru.fortgroup.dpru.adapters.IDataHolder

/**
 * Created by Alex on 07.01.2017.
 */

class TemperatureItem : AbstractItem<TemperatureItem, TemperatureItem.ViewHolder>(), IDataHolder {

    override fun createView(ctx: Context, parent: ViewGroup?): View = createView(AnkoContext.create(ctx, this))
    override fun getViewHolder(v: View): ViewHolder = ViewHolder(v)
    override fun getType(): Int = R.id.next_item_id
    override fun getLayoutRes(): Int = -1

    /**
     * our ViewHolder
     */
    class ViewHolder(view: View) : FastAdapter.ViewHolder<TemperatureItem>(view) {
        /*val mainAuthor: TextView = view.find(R.id.text_main_author)
        val mainTag: TextView = view.find(R.id.text_main_rubric)

        private val mainImage: ImageView = view.find(R.id.image_main)
        private val imageProgress: ProgressBar = view.find(R.id.image_loader)
        private val mainTitle: TextView = view.find(R.id.text_main_title)*/

        override fun bindView(item: TemperatureItem, payloads: MutableList<Any>?) {
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

        override fun unbindView(item: TemperatureItem?) {
           /* mainImage.clear()
            mainAuthor.text = null
            mainTitle.text = null
            mainTag.text = null*/
        }
    }

    companion object : AnkoComponent<TemperatureItem> {
        override fun createView(ui: AnkoContext<TemperatureItem>): View = with(ui) {
            frameLayout {
                //backgroundColor = Color.parseColor("#7987C8")//color(R.color.colorRed)
                id = R.id.main_item_lay
                lparams(matchParent)

                relativeLayout {
                    background = roundedBg(Color.parseColor("#7582C1"), 16f)
                    id = R.id.rounded_bg

                    textView("15") {
                        id = R.id.time_temp_id
                        textSize = 14f
                        typeface = Typeface.DEFAULT_BOLD
                        textColor = Color.WHITE
                    }.lparams {
                        alignParentRight()
                        alignParentTop()
                        rightMargin = dip(8)
                        topMargin = dip(8)
                    }

                    imageView {
                        id = R.id.image_temp_id
                        backgroundColor = Color.BLACK
                    }.lparams(dip(64),dip(64)){
                        rightOf(R.id.time_temp_id)
                        leftMargin = dip(16)
                        centerVertically()
                    }

                }.lparams(matchParent, dip(86)) {
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
            }
        }
    }

}
