package pl.training.goodweather.view.forecast

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_forecast.view.*
import pl.training.goodweather.R
import pl.training.goodweather.model.Forecast
import pl.training.goodweather.formatDate
import pl.training.goodweather.model.City

class ForecastRecyclerAdapter
    (private val city: City, private val listener: (String, Forecast) -> Unit): RecyclerView.Adapter<ForecastRecyclerAdapter.ForecastViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, p1: Int) =
        ForecastViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_forecast,
                parent,
                false
            )
        )
//            = ForecastViewHolder(TextView(parent.context))


    override fun getItemCount() = city.forecastList.size

    override fun onBindViewHolder(p0: ForecastViewHolder, p1: Int) {
        p0.bind(city.forecastList[p1])
    }

    inner class ForecastViewHolder (val view: View): RecyclerView.ViewHolder(view){
        fun bind(forecast: Forecast) = with(forecast){
            view.setOnClickListener{listener(city.name, this)}
            view.forecastDate.text = date.formatDate()
            view.forecastDescription.text = description
            view.forecastMaxTemp.text = high.toString()
            view.forecastMinTemp.text = low.toString()
            Picasso.get().isLoggingEnabled = true
            Picasso.get().load(iconUrl).into(view.forecastIcon)
            Log.i("IMG_URL", iconUrl)
        }
    }
}