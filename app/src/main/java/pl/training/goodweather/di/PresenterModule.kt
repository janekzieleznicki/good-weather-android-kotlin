package pl.training.goodweather.di

import dagger.Module
import dagger.Provides
import pl.training.goodweather.model.Forecast
import pl.training.goodweather.model.WeatherService
import pl.training.goodweather.model.events.EventBus
import pl.training.goodweather.presenter.DetailedForecastPresenter
import pl.training.goodweather.presenter.ForecastPresenter

@Module
class PresenterModule {

    @Provides
    fun forecastPresenter(weatherService: WeatherService, eventBus: EventBus<Forecast>) = ForecastPresenter(weatherService, eventBus)

    @Provides
    fun detailedForecastPresenter(weatherService: WeatherService, eventBus: EventBus<Forecast>) = DetailedForecastPresenter(weatherService, eventBus)

}