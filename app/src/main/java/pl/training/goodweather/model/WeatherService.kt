package pl.training.goodweather.model

import android.util.Log
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import pl.training.goodweather.model.api.WeatherProvider
import pl.training.goodweather.model.database.ForecastRepository
import pl.training.goodweather.toDomainModel

class WeatherService(private val weatherProvider: WeatherProvider, private val forecastRepository: ForecastRepository){

    private val cityChangesSubject = BehaviorSubject.create<City>()
    private val disposableBag = CompositeDisposable()

    fun refreshForecast(city: String){
        disposableBag.add(
        weatherProvider
            .getForecast(city)
            .map { it.toDomainModel() }
            .subscribeOn(Schedulers.io())
            .subscribe(
                {onForecastRefreshed(it)},
                { error -> Log.w(this@WeatherService::class.java.name, error.localizedMessage)}
                )
        )
        forecastRepository.getForecastByCityName(city)?.let {
            cityChangesSubject.onNext(it)
        }
    }

    fun refreshLast(){
        forecastRepository.getLast()?.let {
            cityChangesSubject.onNext(it)
        }
    }

    private fun onForecastRefreshed(city: City) {
        cityChangesSubject.onNext(city)
        forecastRepository.save(city)
    }

    fun cityChanges() : Observable<City> = cityChangesSubject
}