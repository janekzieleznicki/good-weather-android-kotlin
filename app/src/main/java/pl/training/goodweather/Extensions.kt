package pl.training.goodweather

import pl.training.goodweather.model.City
import pl.training.goodweather.model.Forecast
import pl.training.goodweather.model.ForecastResultTo
import pl.training.goodweather.model.ForecastTo

fun ForecastResultTo.toDomainModel() = City(city.id, city.name, city.country, list.map { it.toDomainModel() })

fun ForecastTo.toDomainModel()= Forecast(-1, dt * 1000, weather[0].description, temp.max.toInt(), temp.min.toInt(),
        "http://openweathermap.org/img/w/${weather[0].icon}.png")
