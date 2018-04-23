package com.mincor.kviper.di.contracts

import com.mikepenz.fastadapter.items.AbstractItem
import com.mincor.kviper.models.WeatherDataResponce
import com.mincor.kviper.viper.core.iteractor.IInteractor
import com.mincor.kviper.viper.core.iteractor.IInteractorHandler
import com.mincor.kviper.viper.core.presenter.IBasePresenter
import com.mincor.kviper.viper.core.router.IRouter
import com.mincor.kviper.viper.core.view.IBaseView

interface ISearchWeatherContract {

    interface IPresenter : IBasePresenter<IView> {
        fun onSearchSubmit(word:String)
    }

    interface IView : IBaseView<IPresenter> {
        fun onError(error:String)
        fun onSuccess(searchList:List<AbstractItem<*,*>>)
    }

    interface ISearchRouter : IRouter<IView>{
        fun showSubmitError()
        fun onSearchResult(listDataResponce:WeatherDataResponce)
    }

    interface ISearchInteractor : IInteractor<ISearchInteractorHandler> {
        fun searchWeatherByWord(word:String)
    }

    interface ISearchInteractorHandler : IInteractorHandler {
        fun onSearchedWeatherHandler(weatherData:WeatherDataResponce)
    }

}