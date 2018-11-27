package pl.training.goodweather.view.forecast

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager

import android.view.inputmethod.EditorInfo
import com.jakewharton.rxbinding2.widget.textChanges
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_forecast.*
import org.jetbrains.anko.longToast
import pl.training.goodweather.ForecastApplication
import pl.training.goodweather.R
import pl.training.goodweather.model.Forecast
import pl.training.goodweather.presenter.ForecastPresenter
import pl.training.goodweather.presenter.MvpPresenter
import pl.training.goodweather.view.DetailedForecast.DetailedForecastActivity
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class ForecastActivity : AppCompatActivity(), ForecastView {
    companion object {
        const val INTENT_FORECAST_ID = "FORECAST_ID"
    }

    @Inject
    lateinit var presenter: ForecastPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forecast)
        ForecastApplication.graph.inject(this)
        forecastRecyclerView.layoutManager = LinearLayoutManager(this)
        presenter.attachView(this)
        cityChanges()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { searchEditText.isEnabled = false },
                { searchEditText.isEnabled = true })
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
        runOnUiThread { searchEditText.isEnabled = true }

        longToast(text)
    }

    override fun showForecast(forecastList: List<Forecast>) {
        forecastRecyclerView.adapter = ForecastRecyclerAdapter(forecastList){
            val intent = Intent(this@ForecastActivity, DetailedForecastActivity::class.java)
            intent.putExtra(INTENT_FORECAST_ID,it.id)
            this@ForecastActivity.startActivity(intent)
        }
    }

    override fun cityChanges(): Observable<String> = searchEditText.textChanges()
        .map { it.toString() }
        .filter{it.length > 4}
        .debounce(500, TimeUnit.MILLISECONDS)

}
