package pl.training.goodweather.view.forecast

import io.reactivex.Observable
import pl.training.goodweather.model.City
import pl.training.goodweather.model.Forecast
import pl.training.goodweather.view.MvpView

interface ForecastView : MvpView {

    fun showMessage(text: String)

    fun showForecast(forecastList: City)

    fun cityChanges() : Observable<String>

    fun toggleAvailability(available: Boolean)

    fun setForecastSelectedHandler(forecastSelectedHandler: (Forecast) -> Unit)
}