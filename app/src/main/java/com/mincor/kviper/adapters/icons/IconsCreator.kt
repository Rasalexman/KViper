package com.mincor.kviper.adapters.icons

import android.graphics.drawable.Drawable
import com.mincor.kviper.R

object IconsCreator {

    fun createIconByCode(code: Int, isDay:Boolean = true): Int {
        return when (code) {
            800 -> if(isDay) R.drawable.ic_icon_clear_day_64dp else R.drawable.ic_icon_clear_night_64dp // "clear sky"
            801 -> if(isDay) R.drawable.ic_icon_few_clouds_day_64dp else R.drawable.ic_icon_few_clouds_night_64dp //"few clouds"
            802 -> if(isDay) R.drawable.ic_icon_scattered_clouds_day_64dp else R.drawable.ic_icon_scattered_clouds_night_64dp //"scattered clouds"
            803, //"broken clouds"
            804, //"overcast clouds"
            701, //"mist"
            711, //"smoke"
            721, //"haze"
            731, //"sand, dust whirls"
            741, //"fog"
            751, //"sand"
            761, //"dust"
            762, //"volcanic ash"
            771, //"squalls"
            781 -> R.drawable.ic_icon_mist_64dp //"tornado"
            511 -> R.drawable.ic_icon_freezing_rain_64dp //"freazing rain"
            in 200 until 300 -> R.drawable.ic_icon_thunderstorm_64dp//"thunderstorm"
            in 300 until 400 -> R.drawable.ic_icon_shower_rain_64dp //"drizzle"
            in 500 until 505 -> R.drawable.ic_icon_rain_day_64dp //"rain"
            in 512 until 532 -> R.drawable.ic_icon_shower_rain_64dp //"rain"
            in 600 until 700 -> R.drawable.ic_icon_snow_64dp //"snow"
            in 700 until 800 -> R.drawable.ic_icon_mist_64dp //"Atmosphere"
            900, //"tornado"
            901, //"tropical storm"
            962, //"hurricane"
            902 -> R.drawable.ic_icon_hurricane_64dp //"hurricane"
            903 -> R.drawable.ic_icon_hurricane_64dp //"cold"
            else -> if(isDay) R.drawable.ic_icon_few_clouds_day_64dp else R.drawable.ic_icon_few_clouds_night_64dp
        }
    }
}