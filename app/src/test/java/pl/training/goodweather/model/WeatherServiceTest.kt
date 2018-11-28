package pl.training.goodweather.model

import io.reactivex.Observable
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.mockito.ArgumentMatchers
import org.mockito.Mockito.*
import pl.training.goodweather.model.api.ForecastResultTo
import pl.training.goodweather.model.api.WeatherProvider
import pl.training.goodweather.model.database.ForecastRepository
import pl.training.goodweather.presenter.ForecastPresenter

class WeatherServiceTest {

    private val weatherProvider = mock(WeatherProvider::class.java)
    private val forecastRepository = mock(ForecastRepository::class.java)

    private val weatherService = WeatherService(weatherProvider,forecastRepository)

    private val city = City(1,"warsaw", "poland", ArrayList())
    private val forecastTo = mock(ForecastResultTo::class.java)
    @Before
    fun init() {
        `when`(weatherProvider.getForecast(ArgumentMatchers.anyString())).thenReturn(Observable.just(forecastTo))
    }

    @Test
    fun shouldGetForecastFromProvider(){
        val cityName = "warsaw"
        weatherService.refreshForecast(cityName)
        verify(weatherProvider).getForecast(cityName)
    }
}