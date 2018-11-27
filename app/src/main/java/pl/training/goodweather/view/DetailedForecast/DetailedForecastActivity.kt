package pl.training.goodweather.view.DetailedForecast

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.squareup.picasso.Picasso
import io.reactivex.Observable
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


    @Inject
    lateinit var presenter: DetailedForecastPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detailed_forecast)
        ForecastApplication.graph.inject(this)
        presenter.attachView(this)
    }
}
