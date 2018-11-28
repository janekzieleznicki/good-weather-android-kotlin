package pl.training.goodweather.view.DetailedForecast

import io.reactivex.Observable
import pl.training.goodweather.model.Forecast
import pl.training.goodweather.view.MvpView

interface DetailedForecastView : MvpView{

    fun showForecast(forecast: Forecast)

    fun cityChanges() : Observable<String>

    fun forecastIndexChanges() : Observable<Int>
}