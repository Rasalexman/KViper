package com.mincor.kviper.adapters

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.items.AbstractItem
import com.mincor.kviper.R
import com.mincor.kviper.utils.color
import com.mincor.kviper.utils.roundedBg
import org.jetbrains.anko.*
import ru.fortgroup.dpru.adapters.IDataHolder

/**
 * Created by Alex on 07.01.2017.
 */

class MainItem(val imgCode:Int = 0, val locationName:String = "Location", val weatherDesc:String = "clear", val temperature:String= "1.0℃") : AbstractItem<MainItem, MainItem.ViewHolder>(), IDataHolder {

    override fun createView(ctx: Context, parent: ViewGroup?): View = MainItemUI().createView(AnkoContext.create(ctx, this))
    override fun getViewHolder(v: View): ViewHolder = ViewHolder(v)
    override fun getType(): Int = R.id.main_item_id
    override fun getLayoutRes(): Int = -1

    /**
     * our ViewHolder
     */
    class ViewHolder(view: View) : FastAdapter.ViewHolder<MainItem>(view) {
        private val locationName: TextView = view.find(R.id.location_name_id)
        private val weatherDesc: TextView = view.find(R.id.weather_desc_id)
        private val temperatureCount: TextView = view.find(R.id.temperature_id)

        private val mainImage: ImageView = view.find(R.id.main_image_id)

        override fun bindView(item: MainItem, payloads: MutableList<Any>?) {
            locationName.text = item.locationName
            weatherDesc.text = item.weatherDesc
            temperatureCount.text = item.temperature
        }

        override fun unbindView(item: MainItem?) {
           /* mainImage.clear()
            mainAuthor.text = null
            mainTitle.text = null
            mainTag.text = null*/
        }
    }

    inner class MainItemUI : AnkoComponent<MainItem> {


        override fun createView(ui: AnkoContext<MainItem>): View = with(ui) {
            frameLayout {
                //backgroundColor = Color.parseColor("#7987C8")//color(R.color.colorRed)
                id = R.id.main_item_lay
                lparams(matchParent)

                relativeLayout {
                    background = roundedBg(Color.parseColor("#7987C8"), 16f)
                    id = R.id.rounded_bg

                    imageView {
                        backgroundColor = Color.BLACK
                        id = R.id.main_image_id
                    }.lparams(dip(64), dip(64)){
                        alignParentLeft()
                        setMargins(dip(16), dip(16), 0, 0)
                    }

                    verticalLayout {
                        backgroundColor = Color.TRANSPARENT
                        textView("YOUR CITY") {
                            id = R.id.location_name_id
                            backgroundColor = Color.TRANSPARENT
                            textColor = Color.WHITE
                            textSize = 24f
                            typeface = Typeface.DEFAULT_BOLD
                            maxHeight = dip(100)
                        }
                        textView("Clear sky") {
                            id = R.id.weather_desc_id
                            backgroundColor = Color.TRANSPARENT
                            textColor = Color.LTGRAY
                            textSize = 16f
                        }
                    }.lparams {
                        rightOf(R.id.main_image_id)
                        setMargins(dip(8), dip(16), 0, 0)
                    }

                    textView("1.0\u2103"){
                        id = R.id.temperature_id
                        backgroundColor = Color.TRANSPARENT
                        textColor = Color.WHITE
                        textSize = 24f
                        typeface = Typeface.DEFAULT_BOLD
                    }.lparams {
                        alignParentRight()
                        setMargins(0, dip(16), dip(16), 0)
                    }

                    val iconsSize = 36

                    ///--------- LINEAR 1
                    linearLayout {
                        id = R.id.linear_icons_one_id

                        linearLayout {
                            lparams(matchParent) {
                                weight = 1f
                                rightMargin = dip(8)
                            }

                            imageView {
                                backgroundColor = Color.BLACK
                            }.lparams(dip(iconsSize), dip(iconsSize))

                            verticalLayout {
                                textView("4 mph") {
                                    backgroundColor = Color.TRANSPARENT
                                    textSize = 14f
                                    textColor = Color.WHITE
                                }.lparams(matchParent)
                                textView("West") {
                                    textSize = 14f
                                    textColor = Color.LTGRAY
                                }
                            }.lparams {
                                gravity = Gravity.CENTER_VERTICAL
                                marginStart = dip(8)
                            }
                        }
                        linearLayout {
                            lparams(matchParent) {
                                weight = 1f
                                rightMargin = dip(8)
                            }

                            imageView {
                                backgroundColor = Color.BLACK
                            }.lparams(dip(iconsSize), dip(iconsSize))

                            verticalLayout {
                                textView("4 mph") {
                                    backgroundColor = Color.TRANSPARENT
                                    textSize = 14f
                                    textColor = Color.WHITE
                                }.lparams(matchParent)
                                textView("West") {
                                    textSize = 14f
                                    textColor = Color.LTGRAY
                                }
                            }.lparams {
                                gravity = Gravity.CENTER_VERTICAL
                                marginStart = dip(8)
                            }
                        }
                        linearLayout {
                            lparams(matchParent) {
                                weight = 1f
                                rightMargin = dip(8)
                            }

                            imageView {
                                backgroundColor = Color.BLACK
                            }.lparams(dip(iconsSize), dip(iconsSize))

                            verticalLayout {
                                textView("4 mph") {
                                    textSize = 14f
                                    textColor = Color.WHITE
                                }.lparams(matchParent)
                                textView("West") {
                                    textSize = 14f
                                    textColor = Color.LTGRAY
                                }
                            }.lparams {
                                gravity = Gravity.CENTER_VERTICAL
                                marginStart = dip(8)
                            }
                        }
                    }.lparams(matchParent) {
                        gravity = Gravity.CENTER_HORIZONTAL
                        bottomOf(R.id.main_image_id)
                        setMargins(dip(16), dip(16), dip(16), dip(8))
                    }

                    ///--------- LINEAR 2
                    linearLayout {
                        linearLayout {
                            lparams(matchParent) {
                                weight = 1f
                                rightMargin = dip(8)
                            }

                            imageView {
                                backgroundColor = Color.BLACK
                            }.lparams(dip(iconsSize), dip(iconsSize))

                            verticalLayout {
                                textView("4 mph") {
                                    backgroundColor = Color.TRANSPARENT
                                    textSize = 14f
                                    textColor = Color.WHITE
                                }.lparams(matchParent)
                                textView("West") {
                                    textSize = 14f
                                    textColor = Color.LTGRAY
                                }
                            }.lparams {
                                gravity = Gravity.CENTER_VERTICAL
                                marginStart = dip(8)
                            }
                        }
                        linearLayout {
                            lparams(matchParent) {
                                weight = 1f
                                rightMargin = dip(8)
                            }

                            imageView {
                                backgroundColor = Color.BLACK
                            }.lparams(dip(iconsSize), dip(iconsSize))

                            verticalLayout {
                                textView("4 mph") {
                                    backgroundColor = Color.TRANSPARENT
                                    textSize = 14f
                                    textColor = Color.WHITE
                                }.lparams(matchParent)
                                textView("West") {
                                    textSize = 14f
                                    textColor = Color.LTGRAY
                                }
                            }.lparams {
                                gravity = Gravity.CENTER_VERTICAL
                                marginStart = dip(8)
                            }
                        }
                        linearLayout {
                            lparams(matchParent) {
                                weight = 1f
                                rightMargin = dip(8)
                            }

                            imageView {
                                backgroundColor = Color.BLACK
                            }.lparams(dip(iconsSize), dip(iconsSize))

                            verticalLayout {
                                textView("4 mph") {
                                    textSize = 14f
                                    textColor = Color.WHITE
                                }.lparams(matchParent)
                                textView("West") {
                                    textSize = 14f
                                    textColor = Color.LTGRAY
                                }
                            }.lparams {
                                gravity = Gravity.CENTER_VERTICAL
                                marginStart = dip(8)
                            }
                        }
                    }.lparams(matchParent) {
                        gravity = Gravity.CENTER_HORIZONTAL
                        bottomOf(R.id.linear_icons_one_id)
                        setMargins(dip(16), 0, dip(16), dip(16))
                    }


                }.lparams(matchParent, (context.displayMetrics.widthPixels / 16) * 9) {
                    setMargins(dip(16),dip(16), dip(16), dip(16))
                }

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
