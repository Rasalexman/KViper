package com.mincor.kviper.di.contracts

import com.mikepenz.fastadapter.items.AbstractItem
import com.mincor.kviper.models.WeatherDataResponce
import com.mincor.kviper.models.network.ForecastDataResponce
import com.mincor.kviper.models.network.ListFindDataResponce
import com.mincor.kviper.viper.core.iteractor.IInteractor
import com.mincor.kviper.viper.core.iteractor.IInteractorHandler
import com.mincor.kviper.viper.core.presenter.IBasePresenter
import com.mincor.kviper.viper.core.router.IRouter
import com.mincor.kviper.viper.core.view.IBaseView

interface IMainPageContract {

    interface IPresenter : IBasePresenter<IView> {

    }

    interface IView : IBaseView<IPresenter> {
        fun showLoadingFooter()
        fun onSuccess(list: List<AbstractItem<*, *>>)
        fun onError(error:String)
    }

    interface IMainRouter : IRouter<IView> {
        fun onAllDataReady(list:List<AbstractItem<*,*>>)
        fun onLocationNotEnabledError()
        fun showLoading()
    }

    interface IMainInteractor : IInteractor<IMainInteractorHandler> {
        fun getLocationWeatherDataList(lon:Double, lat:Double)
        fun getCurrentLocationWeather(lon: Double, lat: Double)
    }

    interface IMainInteractorHandler : IInteractorHandler {
        fun onAllMainDataHandler(weather:WeatherDataResponce, forecast:ForecastDataResponce)
        fun onSearchedWeatherListHandler(weatherListResponce:ListFindDataResponce?)
    }

}