package pl.training.goodweather.di

import dagger.Module
import dagger.Provides
import pl.training.goodweather.presenter.ForecastViewPresenter

@Module
class PresenterModule {

    @Provides
    fun forecastViewPresenter() = ForecastViewPresenter()

}