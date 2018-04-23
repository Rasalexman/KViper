package com.mincor.kviper.di.interfaces

import com.mincor.kviper.models.WeatherDataResponce
import com.mincor.kviper.models.network.ForecastDataResponce
import com.mincor.kviper.models.network.ListFindDataResponce
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

    @GET("forecast?")
    @Headers("Content-type: application/json")
    fun getWeatherForecastById(@Query("id") latitude: Int): Call<ForecastDataResponce>

    @GET("forecast?")
    @Headers("Content-type: application/json")
    fun getWeatherForecastByCity(@Query("q") cityName: String): Call<ForecastDataResponce>

    @GET("find?")
    @Headers("Content-type: application/json")
    fun findWeatherByCoordRounds(@Query("lat") latitude: Double, @Query("lon") longtitude: Double, @Query("cnt") count:Int = 25): Call<ListFindDataResponce>

}