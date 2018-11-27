package pl.training.goodweather.view

import io.reactivex.Observable
import pl.training.goodweather.model.City
import pl.training.goodweather.model.Forecast

interface ForecastView : MvpView {

    fun showMessage(text: String)

    fun showForecast(forecastList: List<Forecast>)

    fun placeChanges() : Observable<String>
}