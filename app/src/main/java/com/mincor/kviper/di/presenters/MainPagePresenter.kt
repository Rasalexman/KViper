package com.mincor.kviper.di.presenters

import com.mincor.kviper.common.tracker.GPSTracker
import com.mincor.kviper.di.contracts.IMainPageContract
import com.mincor.kviper.di.interfaces.IWeatherApi
import com.mincor.kviper.models.network.ListFindDataResponce
import com.mincor.kviper.utils.log
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.launch
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton
import ru.gildor.coroutines.retrofit.await


class MainPagePresenter(private val gpsTracker: GPSTracker, weatherApi:IWeatherApi) : IMainPageContract.IPresenter, IMainPageContract.IMainInteractorHandler {

    private var router: IMainPageContract.IMainRouter? = null
    private val interactor: IMainPageContract.IMainInteractor = MainInteractor(this, weatherApi)

    override fun bind(v: IMainPageContract.IView) {
        if(gpsTracker.canGetLocation()){
            router = MainRouter(v)
            interactor.getLocationWeatherDataList(gpsTracker.longitude, gpsTracker.latitude)
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

    inner class MainInteractor(override var output: IMainPageContract.IMainInteractorHandler? = null,
                               private val weatherApi: IWeatherApi) : IMainPageContract.IMainInteractor {

        override fun getLocationWeatherDataList(lon: Double, lat: Double) {
            launch(CommonPool) {
                val result =
                        try {
                            weatherApi.findWeatherByCoordRounds(lat, lon).await()
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
    }


    inner class MainRouter(override var view: IMainPageContract.IView? = null) : IMainPageContract.IMainRouter {

        override fun onLocationNotEnabledError() {
            view?.onError("")
        }

        override fun unbind() {
            view = null
        }
    }
}

val mainModule = Kodein.Module{
    bind<IMainPageContract.IPresenter>() with singleton { MainPagePresenter(instance(), instance()) }
}