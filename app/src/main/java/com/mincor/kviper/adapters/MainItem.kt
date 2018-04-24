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
import com.mincor.kviper.adapters.icons.IconsCreator
import com.mincor.kviper.models.items.MainItemModel
import com.mincor.kviper.utils.color
import com.mincor.kviper.utils.roundedBg
import org.jetbrains.anko.*
import ru.fortgroup.dpru.adapters.IDataHolder

/**
 * Created by Alex on 07.01.2017.
 */

class MainItem(val model:MainItemModel) : AbstractItem<MainItem, MainItem.ViewHolder>(), IDataHolder {

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

        private val windValue: TextView = view.find(R.id.wind_value_id)
        private val windDesc: TextView = view.find(R.id.wind_desc_id)

        private val humidityValue: TextView = view.find(R.id.humidity_value_id)

        private val pressureValue: TextView = view.find(R.id.pressure_value_id)

        private val daySunValue: TextView = view.find(R.id.sun_value_id)
        private val daySunDesc: TextView = view.find(R.id.sun_desc_id)

        private val tempMaxValue: TextView = view.find(R.id.temp_max_value_id)
        private val tempMinValue: TextView = view.find(R.id.temp_min_value_id)

        private val mainImage: ImageView = view.find(R.id.main_image_id)

        override fun bindView(item: MainItem, payloads: MutableList<Any>?) {
            val model = item.model
            locationName.text = model.locationName
            weatherDesc.text = model.weatherDesc
            temperatureCount.text = model.temperature
            mainImage.setImageResource(IconsCreator.createIconByCode(model.imgCode))

            windValue.text = model.windSpeed
            windDesc.text = model.windDirection

            humidityValue.text = model.humidity

            pressureValue.text = model.pressure

            daySunValue.text = model.daySun
            daySunDesc.text = model.daySunDesc

            tempMaxValue.text = model.maxTemp
            tempMinValue.text = model.minTemp
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
                id = R.id.main_item_lay
                lparams(matchParent)

                relativeLayout {
                    background = roundedBg(Color.parseColor("#7987C8"), 16f)
                    id = R.id.rounded_bg

                    linearLayout {
                        id = R.id.weather_block_id
                        imageView {
                            id = R.id.main_image_id
                        }

                        verticalLayout {
                            textView("YOUR CITY") {
                                id = R.id.location_name_id
                                textColor = Color.WHITE
                                textSize = 16f
                                typeface = Typeface.DEFAULT_BOLD
                                maxWidth = dip(150)
                            }
                            textView("Clear sky") {
                                id = R.id.weather_desc_id
                                textColor = Color.LTGRAY
                                textSize = 16f
                            }
                        }.lparams {
                            gravity = Gravity.CENTER_VERTICAL
                            setMargins(dip(8), 0, 0, 0)
                        }
                    }.lparams {
                        alignParentLeft()
                        setMargins(dip(16), dip(16),0,0)
                    }


                    textView("1.0\u2103"){
                        id = R.id.temperature_id
                        textColor = Color.WHITE
                        textSize = 26f
                        typeface = Typeface.DEFAULT_BOLD
                    }.lparams {
                        alignParentRight()
                        setMargins(0, dip(16), dip(16), 0)
                    }

                    val iconsSize = 36
                    val rMargin = 4
                    val sMargin = 4

                    ///--------- LINEAR 1
                    linearLayout {
                        id = R.id.linear_icons_one_id

                        // WIND
                        linearLayout {
                            lparams(matchParent) {
                                weight = 1f
                                rightMargin = dip(rMargin)
                            }

                            imageView(R.drawable.ic_icon_wind_64dp) {
                            }.lparams(dip(iconsSize), dip(iconsSize))

                            verticalLayout {
                                textView("4 mph") {
                                    id = R.id.wind_value_id
                                    textSize = 14f
                                    textColor = Color.WHITE
                                }.lparams(matchParent)
                                textView("West") {
                                    id = R.id.wind_desc_id
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
                                rightMargin = dip(rMargin)
                            }

                            imageView(R.drawable.ic_icon_humidity_64dp) {
                            }.lparams(dip(iconsSize), dip(iconsSize))

                            verticalLayout {
                                textView("4 mph") {
                                    id = R.id.humidity_value_id
                                    textSize = 16f
                                    textColor = Color.WHITE
                                }.lparams(matchParent)
                            }.lparams {
                                gravity = Gravity.CENTER_VERTICAL
                                marginStart = dip(sMargin)
                            }
                        }
                        linearLayout {
                            lparams(matchParent) {
                                weight = 1f
                            }

                            imageView(R.drawable.ic_icon_pressure_64dp) {
                            }.lparams(dip(iconsSize), dip(iconsSize))

                            verticalLayout {
                                textView("762") {
                                    id = R.id.pressure_value_id
                                    textSize = 14f
                                    textColor = Color.WHITE
                                }.lparams(matchParent)
                                textView(R.string.pressure_txt) {
                                    id = R.id.pressure_desc_id
                                    textSize = 14f
                                    textColor = Color.LTGRAY
                                }
                            }.lparams {
                                gravity = Gravity.CENTER_VERTICAL
                                marginStart = dip(sMargin)
                            }
                        }
                    }.lparams(matchParent) {
                        gravity = Gravity.CENTER_HORIZONTAL
                        bottomOf(R.id.weather_block_id)
                        setMargins(dip(16), dip(16), dip(16), 0)
                    }

                    ///--------- LINEAR 2
                    linearLayout {
                        linearLayout {
                            lparams(matchParent) {
                                weight = 1f
                                rightMargin = dip(rMargin)
                            }

                            imageView(R.drawable.ic_icon_sunrise_64dp) {
                            }.lparams(dip(iconsSize), dip(iconsSize))

                            verticalLayout {
                                textView("4 mph") {
                                    id = R.id.sun_value_id
                                    textSize = 14f
                                    textColor = Color.WHITE
                                }.lparams(matchParent)
                                textView("West") {
                                    id = R.id.sun_desc_id
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
                                rightMargin = dip(rMargin)
                            }

                            imageView(R.drawable.ic_icon_temp_max_64dp) {
                            }.lparams(dip(iconsSize), dip(iconsSize))

                            verticalLayout {
                                textView("4 mph") {
                                    id = R.id.temp_max_value_id
                                    textSize = 14f
                                    textColor = Color.WHITE
                                }.lparams(matchParent)
                                textView(R.string.temp_max_txt) {
                                    id = R.id.temp_max_desc_id
                                    textSize = 14f
                                    textColor = Color.LTGRAY
                                }
                            }.lparams {
                                gravity = Gravity.CENTER_VERTICAL
                                marginStart = dip(sMargin)
                            }
                        }
                        linearLayout {
                            lparams(matchParent) {
                                weight = 1f
                            }

                            imageView(R.drawable.ic_icon_temp_min_64dp) {
                            }.lparams(dip(iconsSize), dip(iconsSize))

                            verticalLayout {
                                textView("4 mph") {
                                    id = R.id.temp_min_value_id
                                    textSize = 14f
                                    textColor = Color.WHITE
                                }.lparams(matchParent)
                                textView(R.string.temp_min_txt) {
                                    id = R.id.temp_min_desc_id
                                    textSize = 14f
                                    textColor = Color.LTGRAY
                                }
                            }.lparams {
                                gravity = Gravity.CENTER_VERTICAL
                                marginStart = dip(sMargin)
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
