package com.mincor.kviper.models.network

import com.mincor.kviper.models.WeatherDataResponce

data class ListFindDataResponce(
        val message:String?=null,
        val cod:Int? = null,
        val count:Int? = null,
        val list:List<WeatherDataResponce>? = null)
