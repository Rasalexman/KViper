package com.mincor.kviper.models

data class WeatherDataResponce (
        val coord: Coord? = null,
        val weather: List<Weather>? = null,
        val base: String? = null,
        val main: Main? = null,
        val visibility: Int? = null,
        val wind: Wind? = null,
        val clouds: Clouds? = null,
        val dt: Int? = null,
        val sys: Sys? = null,
        val id: Int? = null,
        val name: String? = null,
        val cod: Int? = null
        )