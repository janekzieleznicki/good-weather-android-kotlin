package pl.training.goodweather

import android.app.Application
import pl.training.goodweather.di.DaggerGraph
import pl.training.goodweather.di.Graph
import pl.training.goodweather.di.ModelModule
import pl.training.goodweather.di.PresenterModule

class ForecastApplication : Application() {

    companion object {

        lateinit var graph: Graph
            private set

    }

    override fun onCreate() {
        super.onCreate()
        graph = DaggerGraph.builder()
            .build()
    }

}