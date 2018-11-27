package pl.training.goodweather.model

import java.util.*

data class City(val providerID: Long, val name: String, val country: String, val forecastList: List<Forecast>)

data class Forecast(val id: Long, val date: Long, val description: String, val high: Int, val low: Int, val iconUrl: String)