package com.mincor.kviper.di.presenters

import android.content.Context
import android.location.Geocoder
import com.mikepenz.fastadapter.items.AbstractItem
import com.mincor.kviper.adapters.MainItem
import com.mincor.kviper.adapters.TemperatureItem
import com.mincor.kviper.common.tracker.GPSTracker
import com.mincor.kviper.db.WeatherDB
import com.mincor.kviper.di.contracts.IMainPageContract
import com.mincor.kviper.di.interfaces.IWeatherApi
import com.mincor.kviper.models.WeatherDataResponce
import com.mincor.kviper.models.items.MainItemModel
import com.mincor.kviper.models.network.ForecastDataResponce
import com.mincor.kviper.models.network.ListFindDataResponce
import com.mincor.kviper.utils.log
import com.mincor.weatherme.R
import com.raizlabs.android.dbflow.kotlinextensions.save
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton
import ru.gildor.coroutines.retrofit.await
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.floor


class MainPagePresenter(
        private val gpsTracker: GPSTracker,
        appContext: Context,
        weatherApi: IWeatherApi
) : IMainPageContract.IPresenter, IMainPageContract.IMainInteractorHandler {

    private var router: IMainPageContract.IMainRouter? = null
    private val interactor: IMainPageContract.IMainInteractor = MainInteractor(this, weatherApi, appContext, gpsTracker)

    override fun bind(v: IMainPageContract.IView) {
        if (gpsTracker.canGetLocation()) {
            router = MainRouter(v)
            router!!.showLoading()
            interactor.getCurrentLocationWeather(gpsTracker.longitude, gpsTracker.latitude)
        } else {
            gpsTracker.showSettingsAlert()
        }
    }

    override fun unbind() {
        router?.unbind()
        router = null
    }

    override fun onSearchedWeatherListHandler(weatherListResponce: ListFindDataResponce?) {

    }

    override fun onErrorHandler() {

    }

    override fun onAllMainDataHandler(weatherList: List<AbstractItem<*, *>>) {
        router?.onAllDataReady(weatherList)
    }


    override fun onResultHandler(result: Any?) {

    }

    class MainInteractor(
            override var output: IMainPageContract.IMainInteractorHandler? = null,
            private val weatherApi: IWeatherApi,
            private val context: Context,
            private val gpsTracker: GPSTracker
    ) : IMainPageContract.IMainInteractor {

        override fun getCurrentLocationWeather(lon: Double, lat: Double) {
            GlobalScope.launch {
                try {
                    val weatherData = withContext(Dispatchers.IO) { weatherApi.getWeatherByCoords(lat, lon).await() }
                    val weatherForecast = withContext(Dispatchers.IO) { weatherApi.getWeatherForecastById(weatherData.id!!).await() }
                    parseWeatherRespond(weatherData, weatherForecast)
                } catch (e: Throwable) {
                    log { e.message }
                    output?.onErrorHandler()
                } catch (e: Exception) {
                    log { e.message }
                    output?.onErrorHandler()
                }
            }
        }

        private fun parseWeatherRespond(weather: WeatherDataResponce, forecast: ForecastDataResponce) {

            var mainItemModel = WeatherDB.weatherModel
            //
            val weatherPlace = weather.name!!
            val temperature = weather.main?.temp?.let {
                "${floor(it).toInt()}℃"
            } ?: "12℃"
            val currentWeather = weather.weather?.get(0)
            val iconId = currentWeather?.id ?: 800
            val weatherDesc = currentWeather?.description
                    ?: context.getString(R.string.clear_weather_txt)
            val geocoder = Geocoder(context.applicationContext, Locale.getDefault())
            val locationName = try {
                val listAddresses = geocoder.getFromLocation(gpsTracker.latitude, gpsTracker.longitude, 1)
                if (listAddresses != null && listAddresses.size > 0) {
                    listAddresses[0].adminArea
                } else {
                    weatherPlace
                }
            } catch (e: IOException) {
                weatherPlace
            }

            val windSpeed = "${floor(weather.wind?.speed
                    ?: 1.0).toInt()} ${context.getString(R.string.wind_speed_txt)}"
            val windDirection = degToCompass(weather.wind?.deg ?: 0.0)

            val humidity = "${floor(weather.main?.humidity ?: 0.0).toInt()}%"
            val pressure = "${floor(weather.main?.pressure ?: 762.0).toInt()}"

            val cal = Calendar.getInstance()
            val currentHour = cal[Calendar.HOUR_OF_DAY]

            val sunsetTime = (weather.sys?.sunset ?: 0) * 1000L
            val sunriseTime = (weather.sys?.sunrise ?: 0) * 1000L

            cal.timeInMillis = sunsetTime
            val sunsetHour = cal[Calendar.HOUR_OF_DAY]
            cal.timeInMillis = sunriseTime
            val sunriseHour = cal[Calendar.HOUR_OF_DAY]

            val isDay = (currentHour < sunriseHour || currentHour > sunsetHour)
            val daySunDesc = if (isDay) context.getString(R.string.sunrise_txt) else context.getString(R.string.sunset_txt)
            val daySunValue = if (isDay) sunriseTime else sunsetTime
            val daySunStr = SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date(daySunValue))

            mainItemModel = mainItemModel ?: MainItemModel()
            mainItemModel.let {
                it.imgCode = iconId
                it.locationName = locationName
                it.weatherDesc = weatherDesc
                it.temperature = temperature
                it.windSpeed = windSpeed
                it.windDirection = windDirection
                it.humidity = humidity
                it.pressure = pressure
                it.daySun = daySunStr
                it.daySunDesc = daySunDesc
                it.minTemp = weather.main?.temp_min.toString()
                it.maxTemp = weather.main?.temp_max.toString()
            }
            mainItemModel.save()

            val itemsList = mutableListOf<AbstractItem<*, *>>(MainItem(mainItemModel))
            forecast.list?.forEach { _ ->
                itemsList.add(TemperatureItem())
            }

            output?.onAllMainDataHandler(itemsList)
        }

        override fun getLocationWeatherDataList(lon: Double, lat: Double) {
            GlobalScope.launch {
                val result =
                        try {
                            withContext(Dispatchers.IO) { weatherApi.findWeatherByCoordRounds(lat, lon).await() }
                        } catch (e: Throwable) {
                            log { e.message }
                            null
                        } catch (e: Exception) {
                            log { e.message }
                            null
                        }
                result?.let {
                    output?.onSearchedWeatherListHandler(result)
                } ?: output?.onErrorHandler()
            }
        }

        override fun unbind() {

        }

        private fun degToCompass(num: Double): String {
            val index = ((num / 22.5) + .5)
            val arr = listOf("Сев.", "ССВ", "СВ.", "ВСВ", "Вост.", "ВЮВ", "ЮВ", "ЮЮВ", "Южный", "ЮЮЗ", "ЮЗ", "ЗЮЗ", "Зап.", "ЗСЗ", "СЗ", "ССЗ")
            return arr[(index % 16).toInt()]
        }
    }


    class MainRouter(
            override var view: IMainPageContract.IView? = null
    ) : IMainPageContract.IMainRouter {

        override fun onAllDataReady(list: List<AbstractItem<*, *>>) {
            view?.onSuccess(list)
        }

        override fun showLoading() {
            view?.showLoading()
        }

        override fun onLocationNotEnabledError() {
            view?.onError("")
        }

        override fun unbind() {
            view = null
        }
    }
}

val mainModule = Kodein.Module("mainModule") {
    bind<IMainPageContract.IPresenter>() with singleton { MainPagePresenter(instance(), instance(), instance()) }
}