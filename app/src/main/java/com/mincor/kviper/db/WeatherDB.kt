package com.mincor.kviper.db

import com.mincor.kviper.models.items.MainItemModel
import com.raizlabs.android.dbflow.annotation.Database
import com.raizlabs.android.dbflow.kotlinextensions.from
import com.raizlabs.android.dbflow.kotlinextensions.select

@Database(version = WeatherDB.VERSION)
object WeatherDB {
    const val VERSION = 17

    val weatherModel = (select from MainItemModel::class).querySingle()
}