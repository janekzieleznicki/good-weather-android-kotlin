package pl.training.goodweather.presenter

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import pl.training.goodweather.model.WeatherService
import pl.training.goodweather.view.ForecastView

class ForecastPresenter(private val weatherService: WeatherService) : MvpPresenter<ForecastView> {

    private var view : ForecastView? = null
    private val disposableBag = CompositeDisposable()

    override fun attachView(view: ForecastView) {
        this.view = view
        view.showMessage("Specify place")
        bindView()
    }

    override fun detachView() {
        this.view = null
        disposableBag.clear()
    }

    override fun bindView() {
        view?.let {
            disposableBag.add(
                it.placeChanges().subscribe{place -> loadForecast(place)}
            )
        }
    }

    fun loadForecast(city: String = "warsaw") {
        val subscription = weatherService.getForecast(city)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    view?.showForecast(it.forecastList)
                },
                {
                    view?.showMessage(it.localizedMessage)
                    it.printStackTrace() })
        disposableBag.add(subscription)
    }

}