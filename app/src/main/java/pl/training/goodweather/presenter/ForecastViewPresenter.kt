package pl.training.goodweather.presenter

import pl.training.goodweather.view.ForecastView

class ForecastViewPresenter : MvpPresenter<ForecastView> {

    private var view : ForecastView? = null

    override fun attachView(view: ForecastView) {
        this.view = view
        view.showMessage("Refreshing weather...")
    }

    override fun detachView() {
        this.view = null
    }

}