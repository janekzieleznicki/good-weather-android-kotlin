package pl.training.goodweather.view.DetailedForecast

import pl.training.goodweather.model.Forecast
import pl.training.goodweather.view.MvpView

interface DetailedForecastView : MvpView{

    fun showForecast(forecast: Forecast)

}