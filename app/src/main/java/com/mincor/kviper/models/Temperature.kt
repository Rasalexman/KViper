package com.mincor.kviper.models

data class Temperature (val temp:Double? = null,
                        val temp_min:Double? = null, val temp_max:Double? = null,
                        val pressure:Double? = null, val sea_level:Double? = null,
                        val grnd_level:Double? = null, val humidity:Int? = null, val temp_kf:Double? = null)
