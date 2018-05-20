package com.mincor.kviper.models.items

import com.mincor.kviper.db.WeatherDB
import com.raizlabs.android.dbflow.annotation.Column
import com.raizlabs.android.dbflow.annotation.PrimaryKey
import com.raizlabs.android.dbflow.annotation.Table

@Table(database = WeatherDB::class, cachingEnabled = true)
data class MainItemModel(@PrimaryKey var guid:Int = 0,
                         @Column var imgCode:Int = 0,
                         @Column var locationName:String = "Location",
                         @Column var weatherDesc:String = "clear",
                         @Column var temperature:String= "1.0℃",
                         @Column var windSpeed:String = "3.0",
                         @Column var windDirection:String = "N",
                         @Column var humidity:String = "0%",
                         @Column var pressure:String = "762",
                         @Column var daySun:String = "07:20",
                         @Column var daySunDesc:String = "sunset",
                         @Column var minTemp:String = "5℃",
                         @Column var maxTemp:String = "12℃")