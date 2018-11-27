package pl.training.goodweather.di

import dagger.Module
import dagger.Provides
import pl.training.goodweather.model.WeatherProvider
import pl.training.goodweather.model.WeatherService
import pl.training.goodweather.presenter.ForecastPresenter

@Module
class PresenterModule {

    @Provides
    fun forecastPresenter(weatherService: WeatherService) = ForecastPresenter(weatherService)

}