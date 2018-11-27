package pl.training.goodweather.presenter

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import pl.training.goodweather.model.WeatherService
import pl.training.goodweather.view.forecast.ForecastView

class ForecastPresenter(private val weatherService: WeatherService) : MvpPresenter<ForecastView> {

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
            disposableBag.add(
                it.cityChanges()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe{ city -> weatherService.refreshForecast(city) })}

        disposableBag.add(weatherService.cityChanges()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    view?.showForecast(it.forecastList)
                    view?.showMessage(it.name)
                },
                {
                    view?.showMessage(it.localizedMessage)
                    it.printStackTrace() }))
    }

    fun loadForecast(city: String = "warsaw") {
        weatherService.refreshForecast(city)
    }

}