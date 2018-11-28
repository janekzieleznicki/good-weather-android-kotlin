package pl.training.goodweather.view.DetailedForecast

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.squareup.picasso.Picasso
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import kotlinx.android.synthetic.main.activity_detailed_forecast.*
import kotlinx.android.synthetic.main.item_forecast.view.*
import pl.training.goodweather.ForecastApplication
import pl.training.goodweather.R
import pl.training.goodweather.formatDate
import pl.training.goodweather.model.Forecast
import pl.training.goodweather.presenter.DetailedForecastPresenter
import pl.training.goodweather.presenter.ForecastPresenter
import pl.training.goodweather.presenter.MvpPresenter
import pl.training.goodweather.view.forecast.ForecastActivity
import pl.training.goodweather.view.forecast.ForecastView
import javax.inject.Inject

class DetailedForecastActivity : AppCompatActivity(), DetailedForecastView {

    var cityChanges = BehaviorSubject.create<String>()
    var forecastChanges = BehaviorSubject.create<Int>()

    @Inject
    lateinit var presenter : DetailedForecastPresenter

    override fun showForecast(forecast: Forecast) {
        with(forecast){
            forecastDate.text = date.formatDate()
            forecastDescription.text = description
            forecastMaxTemp.text = high.toString()
            forecastMinTemp.text = low.toString()
            Picasso.get().isLoggingEnabled = true
            Picasso.get().load(iconUrl).into(forecastIcon)
            Log.i("IMG_URL", iconUrl)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detailed_forecast)
        ForecastApplication.graph.inject(this)
        presenter.attachView(this)
        forecastChanges.onNext(intent.getIntExtra(ForecastActivity.INTENT_FORECAST_ID,0))
        cityChanges.onNext(intent.getStringExtra(ForecastActivity.INTENT_CITY_NAME))
        initView()
    }

    private fun initView(){
        title = getCityName()
    }

    private fun getCityName() = intent.getStringExtra(ForecastActivity.INTENT_CITY_NAME)

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }

    override fun cityChanges(): Observable<String> = cityChanges
    override fun forecastIndexChanges(): Observable<Int> = forecastChanges
}
