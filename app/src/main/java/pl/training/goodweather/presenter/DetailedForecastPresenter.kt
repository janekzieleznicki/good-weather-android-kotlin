package pl.training.goodweather.presenter

import pl.training.goodweather.model.WeatherService
import pl.training.goodweather.view.DetailedForecast.DetailedForecastView
import javax.inject.Inject

class DetailedForecastPresenter(private val weatherService: WeatherService) : MvpPresenter<DetailedForecastView> {


    override fun attachView(view: DetailedForecastView) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun detachView() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun bindView() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}