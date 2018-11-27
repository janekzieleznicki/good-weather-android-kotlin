package pl.training.goodweather.model

import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import pl.training.goodweather.model.api.WeatherProvider
import pl.training.goodweather.toDomainModel

class WeatherService(private val weatherProvider: WeatherProvider){

    fun getForecast(city: String): Observable<City> {
        return weatherProvider.getForecast(city)
            .map{it.toDomainModel()}
            .subscribeOn(Schedulers.io())
    }
}