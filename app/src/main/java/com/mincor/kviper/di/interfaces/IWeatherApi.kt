package com.mincor.kviper.di.interfaces

import com.mincor.kviper.models.WeatherDataResponce
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface IWeatherApi {

    @GET("weather?")
    @Headers("Content-type: application/json")
    fun getWeatherByCity(@Query("q") cityName: String): Call<WeatherDataResponce>

    @GET("weather?")
    @Headers("Content-type: application/json")
    fun getWeatherByCoords(@Query("lat") latitude: Number, @Query("lon") longtitude: Number): Call<WeatherDataResponce>

}