package pl.training.goodweather.presenter

import android.util.Log
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import pl.training.goodweather.model.Forecast
import pl.training.goodweather.model.WeatherService
import pl.training.goodweather.model.events.EventBus
import pl.training.goodweather.view.DetailedForecast.DetailedForecastView

class DetailedForecastPresenter(
    private val weatherService: WeatherService,
    private val eventBus: EventBus<Forecast>
) : MvpPresenter<DetailedForecastView> {
    private var view : DetailedForecastView? = null
    private val disposableBag = CompositeDisposable()

    override fun attachView(view: DetailedForecastView) {
        this.view = view
        disposableBag.add(eventBus.get().subscribe{
            Log.d(this@DetailedForecastPresenter::class.java.name, it.toString())
            view?.showForecast(it)
        })
        bindView()
    }

    override fun detachView() {
        this.view = null
        disposableBag.clear()
    }

    override fun bindView() {
//        view?.let {
//            weatherService.currentForecast?.let {
//                view?.showForecast(it)
//            }
//        }
    }
    fun onCityChanges(cityName: String){
        disposableBag.add(
            weatherService
                .refreshForecast(cityName)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe{
                    view?.showForecast(it.forecastList[0])
                }
        )
    }
}