package pl.training.goodweather.view.forecast

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager

import com.jakewharton.rxbinding2.widget.textChanges
import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_forecast.*
import org.jetbrains.anko.longToast
import pl.training.goodweather.ForecastApplication
import pl.training.goodweather.R
import pl.training.goodweather.model.City
import pl.training.goodweather.model.Forecast
import pl.training.goodweather.presenter.ForecastPresenter
import pl.training.goodweather.view.DetailedForecast.DetailedForecastActivity
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class ForecastActivity : AppCompatActivity(), ForecastView {
    companion object {

        const val INTENT_FORECAST_ID = "FORECAST_ID"
        const val INTENT_CITY_NAME = "CITY_NAME"

    }

    @Inject
    lateinit var presenter: ForecastPresenter

    var onForecastSelectionHandler: (Forecast) -> Unit = {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forecast)
        ForecastApplication.graph.inject(this)
        forecastRecyclerView.layoutManager = LinearLayoutManager(this)
        presenter.attachView(this)
//        searchEditText.setOnEditorActionListener{
//                view, actionId, event ->
//            if(actionId == EditorInfo.IME_ACTION_DONE){
//                this.presenter.loadForecast(view.text.toString())
//                true
//            }
//            else { false}
//        }
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }

    override fun showMessage(text: String) {
        longToast(text)
    }

    override fun showForecast(forecastList: City) {
        forecastRecyclerView.adapter = ForecastRecyclerAdapter(forecastList){
            cityName, forecast ->
            onForecastSelectionHandler(forecast)
            val intent = Intent(this@ForecastActivity, DetailedForecastActivity::class.java)
            intent.putExtra(INTENT_CITY_NAME, cityName)
            intent.putExtra(INTENT_FORECAST_ID,forecast.id)
            this@ForecastActivity.startActivity(intent)
        }
    }

    override fun cityChanges(): Observable<String> = searchEditText.textChanges()
        .map { it.toString() }
        .filter{it.length > 4}
        .debounce(750, TimeUnit.MILLISECONDS)

    override fun toggleAvailability(available: Boolean) {
        runOnUiThread { searchEditText.isEnabled = available }
    }

    override fun setForecastSelectedHandler(forecastSelectedHandler: (Forecast) -> Unit) {
        onForecastSelectionHandler = forecastSelectedHandler
    }
}
