package pl.training.goodweather.view

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import org.jetbrains.anko.toast
import pl.training.goodweather.ForecastApplication
import pl.training.goodweather.R
import pl.training.goodweather.presenter.ForecastViewPresenter
import javax.inject.Inject

class ForecastActivity : AppCompatActivity(), ForecastView {

    @Inject
    lateinit var presenter: ForecastViewPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forcast)
        ForecastApplication.graph.inject(this)
        presenter.attachView(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }

    override fun showMessage(text: String) {
        toast(text)
    }

}
