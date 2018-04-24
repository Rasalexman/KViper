package com.mincor.kviper.models

data class Forecast (val dt:Long? = null, val main:Temperature? = null,
                     val weather:List<Weather>? = null, val clouds:Clouds? = null,
                     val wind:Wind? = null, val sys:Sys? = null,val dt_txt:String? = null)