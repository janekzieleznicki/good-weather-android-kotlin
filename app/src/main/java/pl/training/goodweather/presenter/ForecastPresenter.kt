package pl.training.goodweather.presenter

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import pl.training.goodweather.model.City
import pl.training.goodweather.model.Forecast
import pl.training.goodweather.model.WeatherService
import pl.training.goodweather.model.events.EventBus
import pl.training.goodweather.view.forecast.ForecastView

class ForecastPresenter(
    private val weatherService: WeatherService,
    private val eventBus: EventBus<Forecast>
) : MvpPresenter<ForecastView> {

    private var view : ForecastView? = null
    private val disposableBag = CompositeDisposable()

    override fun attachView(view: ForecastView) {
        this.view = view
        view.showMessage("Specify city")
        bindView()
        weatherService.refreshLast()
    }

    override fun detachView() {
        this.view = null
        disposableBag.clear()
    }

    override fun bindView() {
        view?.let {

            val placeChanges = it.cityChanges()
                .observeOn(AndroidSchedulers.mainThread())
            disposableBag.addAll(
                placeChanges.subscribe(this::onCityChanges),
                placeChanges.subscribe { view?.toggleAvailability(false) }
            )
            val forecastChanges = weatherService.cityChanges()
                .observeOn(AndroidSchedulers.mainThread())
            disposableBag.addAll(
                forecastChanges.subscribe { forecast -> view?.showForecast(forecast) },
                forecastChanges.subscribe { view?.toggleAvailability(true) },
                forecastChanges.subscribe( {}, {view?.toggleAvailability(true)}),
                forecastChanges.subscribe( {}, { error -> view?.showMessage(error.localizedMessage)}),
                forecastChanges.subscribe {forecast -> view?.showMessage("${forecast.country}: ${forecast.name}")}
            )
            it.setForecastSelectedHandler {
                weatherService.currentForecast = it
            }
        }
    }

    private fun onCityChanges(city: String){
        disposableBag.add(
            weatherService.refreshForecast(city)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    view?.showMessage("Forecast refreshed for ${it.name}, ${it.country}")
                }, {
                    view?.toggleAvailability(true)
                    view?.showMessage(it.localizedMessage)
                })
        )
    }

    override fun viewWillChange() {
        weatherService.currentForecast?.let { eventBus.publish(it) }
    }
}