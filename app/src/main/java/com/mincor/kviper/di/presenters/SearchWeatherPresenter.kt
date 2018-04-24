package com.mincor.kviper.di.presenters

import com.mincor.kviper.adapters.MainItem
import com.mincor.kviper.di.contracts.ISearchWeatherContract
import com.mincor.kviper.di.interfaces.IWeatherApi
import com.mincor.kviper.models.WeatherDataResponce
import com.mincor.kviper.utils.log
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton
import ru.gildor.coroutines.retrofit.await

class SearchWeatherPresenter(weatherApi: IWeatherApi) : ISearchWeatherContract.IPresenter, ISearchWeatherContract.ISearchInteractorHandler {

    private var router: ISearchWeatherContract.ISearchRouter? = null
    private val interactor: ISearchWeatherContract.ISearchInteractor = SearchWeatherInteractor(this, weatherApi)

    private var weatherDataResponce:WeatherDataResponce? = null

    ///------ SEARCH WEATHER BY WORD FROM VIEW
    override fun onSearchSubmit(word: String) {
        if (word.isNotEmpty() && word.isNotBlank()) {
            interactor.searchWeatherByWord(word)
        } else router?.showSubmitError()
    }

    ///------ SEARCH BY WORD
    override fun onSearchedWeatherHandler(weatherData: WeatherDataResponce) {
        weatherDataResponce = weatherData
        router?.onSearchResult(weatherData)
    }

    override fun onResultHandler(result: Any?) {
        val weatherData = result as? WeatherDataResponce
        weatherData?.let {
            weatherDataResponce = result
            router?.onSearchResult(result)
        }

    }

    override fun onErrorHandler() {
        router?.showSubmitError()
    }

    override fun bind(v: ISearchWeatherContract.IView) {
        router = SearchWeatherRouter(v)
    }

    override fun unbind() {
        router?.unbind()
        router = null
    }

    inner class SearchWeatherInteractor(
            override val output: ISearchWeatherContract.ISearchInteractorHandler? = null,
            private val weatherApi: IWeatherApi
    ) : ISearchWeatherContract.ISearchInteractor {

        private var lastWord:String = ""

        /**
         * Search current weather by city name
         */
        override fun searchWeatherByWord(word: String) {
            if(word == lastWord) return
            lastWord = word

            launch(CommonPool) {
                val result =
                        try {
                            getWeatherData(word)
                        } catch (e: Throwable) {
                            log { e.message }
                            null
                        } catch (e: Exception) {
                            log { e.message }
                            null
                        }
                result?.let {
                    output?.onSearchedWeatherHandler(result)
                } ?: output?.onErrorHandler()
            }
        }

        private suspend fun getWeatherData(word: String) = weatherApi.getWeatherByCity(word).await()

        override fun unbind() {

        }
    }

    inner class SearchWeatherRouter(override var view: ISearchWeatherContract.IView? = null) : ISearchWeatherContract.ISearchRouter {

        override fun showSubmitError() {

        }

        override fun onSearchResult(listDataResponce: WeatherDataResponce) {
            /*launch(UI) {
                view?.onSuccess(listOf(MainItem()))
            }*/

        }

        override fun unbind() {
            view = null
        }
    }
}

val searchModule = Kodein.Module {
    bind<ISearchWeatherContract.IPresenter>() with singleton { SearchWeatherPresenter(instance()) }
}