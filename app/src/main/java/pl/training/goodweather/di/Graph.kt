package pl.training.goodweather.di

import dagger.Component
import pl.training.goodweather.view.DetailedForecast.DetailedForecastActivity
import pl.training.goodweather.view.DetailedForecast.DetailedForecastView
import pl.training.goodweather.view.forecast.ForecastActivity
import pl.training.goodweather.view.forecast.ForecastView
import javax.inject.Singleton

@Component(modules = [ModelModule::class, PresenterModule::class])
@Singleton
interface Graph {

    fun inject(forecastView: ForecastActivity)

    fun inject(detailedForecastView: DetailedForecastActivity)

}