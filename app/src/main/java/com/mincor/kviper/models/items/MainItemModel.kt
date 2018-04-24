package com.mincor.kviper.models.items

data class MainItemModel(val imgCode:Int = 0,
                         val locationName:String = "Location",
                         val weatherDesc:String = "clear",
                         val temperature:String= "1.0℃",
                         val windSpeed:String = "3.0",
                         val windDirection:String = "N",
                         val humidity:String = "0%",
                         val pressure:String = "762",
                         val daySun:String = "07:20",
                         val daySunDesc:String = "sunset",
                         val minTemp:String = "5℃",
                         val maxTemp:String = "12℃")