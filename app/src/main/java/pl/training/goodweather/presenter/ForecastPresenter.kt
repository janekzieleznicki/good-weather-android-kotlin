package pl.training.goodweather.presenter

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.doAsync
import pl.training.goodweather.model.WeatherProvider
import pl.training.goodweather.view.ForecastView

class ForecastPresenter (private val weatherProvider: WeatherProvider): MvpPresenter<ForecastView> {

    private var view : ForecastView? = null
    private val disposableBag = CompositeDisposable()

    override fun attachView(view: ForecastView) {
        this.view = view
//        view.showMessage(weatherProvider.getForecast("warsaw").toString())
//        view.showMessage("Refreshing weather...")
        loadForecast()
    }

    override fun detachView() {
        disposableBag.clear()
        this.view = null
    }

    private fun loadForecast(){
        disposableBag.add(
        weatherProvider.getForecast("Warsaw")
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ view?.showMessage(it.toString()) },
                { view?.showMessage(it.message ?: "Error")
                it.printStackTrace()}
            )
        )
    }
}