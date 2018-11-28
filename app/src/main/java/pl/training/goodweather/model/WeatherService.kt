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
    private val tag = this@WeatherService::class.java.name

    private val cityChangesSubject = BehaviorSubject.create<City>()
    private val disposableBag = CompositeDisposable()
    var currentForecast: Forecast? = null

    fun refreshForecast(city: String) : Observable<City>{
        val forecastChanges = weatherProvider.getForecast(city)
            .map { it.toDomainModel() }
            .subscribeOn(Schedulers.io())
            .share()

        disposableBag.addAll(
            forecastChanges.subscribe(
                this::onForecastRefreshed
            ) {
                Log.d(tag, "Refreshing weather failed: "+it.localizedMessage)
//                cityChangesSubject.onError(it)
            }
        )
        forecastRepository.getForecastByCityName(city)?.let {
            cityChangesSubject.onNext(it)
        }
        return forecastChanges
    }

    fun refreshLast(){
        forecastRepository.getLast()?.let {
            cityChangesSubject.onNext(it)
            refreshForecast(it.name)
        }
    }

    private fun onForecastRefreshed(city: City) {
        cityChangesSubject.onNext(city)
        forecastRepository.save(city)
    }

    fun cityChanges() : Observable<City> = cityChangesSubject
}