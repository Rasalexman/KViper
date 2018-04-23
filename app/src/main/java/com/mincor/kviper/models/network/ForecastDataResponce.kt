package com.mincor.kviper.models.network

import com.mincor.kviper.models.City
import com.mincor.kviper.models.Forecast

data class ForecastDataResponce(val code:Int? = null, val message:String? = null,
                                val city:City?, val cnt:Int? = null, val list:List<Forecast>? = null)